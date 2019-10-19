package ru.vsu.cs.course2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

public class Curve {
    private Vector2[] keyPoints;
    private TreeMap<Double, Vector2> points = new TreeMap<>();

    public Curve(List<Vector2> keyPoints) {
        this.keyPoints = addMiddlePoints(keyPoints).toArray(new Vector2[0]);
    }

    private Vector2 getValueAt(double position) {
        int length = keyPoints.length;
        int s = (int) (position * length);
        int n = (int) (position * length - 1) / 2;
        if (s < 0 || s > length)
            throw new IllegalArgumentException();
        if (s == length - 1)
            return keyPoints[length - 1];

        double amount = position * length % 1;
        if (s == 0) {
            return Vector2.lerp(keyPoints[0], keyPoints[1], amount);
        } else if (s >= length - 2) {
            return Vector2.lerp(keyPoints[length - 2], keyPoints[length - 1], amount);
        }

        double bmount = ((position * length - 1) % 2) / 2;
        Vector2 a = keyPoints[2 * n + 1];
        Vector2 b = keyPoints[2 * n + 2];
        Vector2 c = keyPoints[2 * n + 3];
        return Vector2.bezier(a, b, c, bmount);
    }

    private static ArrayList<Vector2> addMiddlePoints(List<Vector2> wayPoints) {
        ArrayList<Vector2> points = new ArrayList<>();
        int count = wayPoints.size();
        if (count <= 2) {
            points.addAll(wayPoints);
        } else {
            Vector2 a, b;
            a = wayPoints.get(0);
            points.add(a);
            for (int i = 1; i < count; i++) {
                b = wayPoints.get(i);
                points.add(Vector2.lerp(a, b, .5));
                points.add(b);
                a = b;
            }
        }
        return points;
    }

    public void splitAt(double position) {
        points.put(position, getValueAt(position));
    }

    public List<Vector2> getPointsProcessed() {
        if (keyPoints.length <= 2)
            return Arrays.asList(keyPoints);
        for (double i = 0; i <= 1; i += 1.0 / (10 * keyPoints.length))
            splitAt(i);
        return new ArrayList<>(points.values());
    }

    public List<Vector2> getPoints() {
        if (keyPoints.length <= 2)
            return Arrays.asList(keyPoints);
        return new ArrayList<>(points.values());
    }
}
