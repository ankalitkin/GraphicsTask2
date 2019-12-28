package ru.vsu.cs.course2.figures;

import ru.vsu.cs.course2.ScreenConverter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class ClosedCurved extends Drawable {
    private static final int SMOOTHNESS = 20;
    private Drawable drawable;

    public ClosedCurved(Drawable drawable) {
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
            for (int i = 1; i <= count; i++) {
                b = wayPoints.get(i % count);
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
        int s = (int) (position * (length - 1));
        int n = (int) ((position * (length - 1) - 1) / 2);
        if (s < 0 || s > length)
            throw new IllegalArgumentException();

        double amount = position * (length - 1) % 1;
        if (s == 0 || s == length - 1) {
            return bezier(keyPoints[length - 2], keyPoints[0], keyPoints[1], (1 + amount) / 2);
        } else if (s >= length - 2) {
            return bezier(keyPoints[length - 2], keyPoints[0], keyPoints[1], amount / 2);
        }

        amount = ((position * (length - 1) - 1) % 2) / 2;
        RealPoint a = keyPoints[2 * n + 1];
        RealPoint b = keyPoints[2 * n + 2];
        RealPoint c = keyPoints[2 * n + 3];
        return bezier(a, b, c, amount);
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

        List<RealPoint> realPoints = new ArrayList<>(pointMap.values());
        realPoints.add(realPoints.get(0));
        return realPoints;
    }

    @Override
    public void draw(ScreenConverter screenConverter, Graphics2D graphics2D) {
        //Nothing to draw now
        drawable.draw(screenConverter, graphics2D);
    }
}
