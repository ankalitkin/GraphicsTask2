package ru.vsu.cs.course2.figures;

import ru.vsu.cs.course2.Plane;
import ru.vsu.cs.course2.ScreenConverter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class Curved implements Drawable {
    private static final int SMOOTHNESS = 20;
    private Drawable drawable;

    public Curved(Drawable drawable) {
        this.drawable = drawable;
    }

    private static ArrayList<RealPoint> addMiddlePoints(List<RealPoint> wayPoints) {
        ArrayList<RealPoint> points = new ArrayList<>();
        int count = wayPoints.size();
        if (count <= 2) {
            points.addAll(wayPoints);
        } else {
            RealPoint a, b;
            a = wayPoints.get(0);
            points.add(a);
            for (int i = 1; i < count; i++) {
                b = wayPoints.get(i);
                points.add(lerp(a, b, .5));
                points.add(b);
                a = b;
            }
        }
        return points;
    }

    private static RealPoint lerp(RealPoint v1, RealPoint v2, double value) {
        double newX = v1.getX() * (1 - value) + v2.getX() * value;
        double newY = v1.getY() * (1 - value) + v2.getY() * value;
        return new RealPoint(newX, newY);
    }

    private static RealPoint bezier(RealPoint a, RealPoint b, RealPoint c, double amount) {
        RealPoint posA = lerp(a, b, amount);
        RealPoint posB = lerp(b, c, amount);
        return lerp(posA, posB, amount);
    }

    private RealPoint getValueAt(RealPoint[] keyPoints, double position) {
        int length = keyPoints.length;
        int s = (int) (position * length);
        int n = (int) (position * length - 1) / 2;
        if (s < 0 || s > length)
            throw new IllegalArgumentException();
        if (s == length - 1)
            return keyPoints[length - 1];

        double amount = position * length % 1;
        if (s == 0) {
            return lerp(keyPoints[0], keyPoints[1], amount);
        } else if (s >= length - 2) {
            return lerp(keyPoints[length - 2], keyPoints[length - 1], amount);
        }

        double bmount = ((position * length - 1) % 2) / 2;
        RealPoint a = keyPoints[2 * n + 1];
        RealPoint b = keyPoints[2 * n + 2];
        RealPoint c = keyPoints[2 * n + 3];
        return bezier(a, b, c, bmount);
    }

    @Override
    public List<RealPoint> getOutlinePoints() {
        List<RealPoint> points = addMiddlePoints(drawable.getOutlinePoints());
        if (points.size() <= 2)
            return new ArrayList<>(points);
        RealPoint[] keyPoints = points.toArray(new RealPoint[0]);

        TreeMap<Double, RealPoint> pointMap = new TreeMap<>();
        for (double i = 0; i <= 1; i += 1.0 / (SMOOTHNESS * points.size()))
            pointMap.put(i, getValueAt(keyPoints, i));

        return new ArrayList<>(pointMap.values());
    }

    @Override
    public void draw(ScreenConverter screenConverter, Graphics2D graphics2D) {
        //Nothing to draw now
        drawable.draw(screenConverter, graphics2D);
    }

}
