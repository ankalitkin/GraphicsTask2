package ru.vsu.cs.course2;

public class Vector2 {
    private final float Smoothness = 0.9999f;
    private final float LenThresholdSqr = 1f;

    double x, y;

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Vector2 plus(Vector2 v2) {
        return new Vector2(x + v2.getX(), y + v2.getY());
    }

    public Vector2 minus(Vector2 v2) {
        return new Vector2(x - v2.getX(), y - v2.getY());
    }

    public static double dot(Vector2 v1, Vector2 v2) {
        return v1.getX() * v2.getX() + v1.getX() * v2.getY();
    }

    public double dot(Vector2 v2) {
        return dot(this, v2);
    }

    public static Vector2 lerp(Vector2 v1, Vector2 v2, double value) {
        double newX = v1.getX() * (1 - value) + v2.getX() * value;
        double newY = v1.getY() * (1 - value) + v2.getY() * value;
        return new Vector2(newX, newY);
    }

    public double sqrMagnitude() {
        return x * x + y * y;
    }

    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }

    public Vector2 normalized() {
        double magnitude = magnitude();
        return new Vector2(x / magnitude, y / magnitude);
    }

    public static double dot(Vector2 a, Vector2 b, Vector2 c) {
        Vector2 v1 = b.minus(a).normalized();
        Vector2 v2 = c.minus(b).normalized();
        return dot(v1, v2);
    }

    public static Vector2 bezier(Vector2 a, Vector2 b, Vector2 c, double amount) {
        Vector2 posA = lerp(a, b, amount);
        Vector2 posB = lerp(b, c, amount);
        return lerp(posA, posB, amount);
    }

}

