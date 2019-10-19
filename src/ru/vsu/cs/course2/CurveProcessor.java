package ru.vsu.cs.course2;

import java.util.ArrayList;
import java.util.List;

public class CurveProcessor {
    public static final int MULTIPLIER = 10;
    List<Vector2> first, second;

    public CurveProcessor(Plane plane1, Plane plane2) {
        List<Vector2> points1 = plane1.getPoints();
        List<Vector2> points2 = plane2.getPoints();
        int count = MULTIPLIER * Math.max(points1.size(), points2.size());
        Curve curve1 = new Curve(points1);
        Curve curve2 = new Curve(points2);
        for (int i = 0; i <= count; i++) {
            double position = (double) i / count;
            curve1.splitAt(position);
            curve2.splitAt(position);
        }
        first = curve1.getPoints();
        second = curve2.getPoints();
    }

    public ArrayList<Vector2> lerp(double amount) {
        int length = Math.min(first.size(), second.size());
        ArrayList<Vector2> res = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            res.add(Vector2.lerp(first.get(i), second.get(i), amount));
        }
        return res;
    }
}
