package ru.vsu.cs.course2;

import ru.vsu.cs.course2.figures.RealPoint;

import java.util.ArrayList;
import java.util.List;

class Plane {
    class Point {
        RealPoint vect;
        int z;

        Point(RealPoint vect, int z) {
            this.vect = vect;
            this.z = z;
        }
    }

    ArrayList<Point> points = new ArrayList<>();
    private int z;

    public Point newPoint(ScreenConverter sc, int x, int y, int z) {
        RealPoint realPoint = VectorFromScreen(sc, x, y);
        return new Point(realPoint, z);
    }

    public RealPoint VectorFromScreen(ScreenConverter sc, int x, int y) {
        ScreenPoint screenPoint = new ScreenPoint(x, y);
        return sc.screenToReal(screenPoint);
    }

    int nextZ() {
        return z++;
    }

    Point getClosest(ScreenConverter sc, int x, int y, int dx, int dy) {
        Point closest = null;
        for (Point point : points) {
            if (Math.abs(x - getX(sc, point)) < dx
                    && Math.abs(y - getY(sc, point)) < dy
                    && (closest == null || point.z > closest.z)) {
                closest = point;
            }
        }
        return closest;
    }

    Point addNewPoint(ScreenConverter sc, int x, int y) {
        Point p = newPoint(sc, x, y, nextZ());
        points.add(p);
        return p;
    }

    private int getX(ScreenConverter sc, Point point) {
        return sc.realToScreen(point.vect).x;
    }

    private int getY(ScreenConverter sc, Point point) {
        return sc.realToScreen(point.vect).y;
    }

    public List<RealPoint> getPoints() {
        ArrayList<RealPoint> list = new ArrayList<>(points.size());
        for (Point point : points) {
            list.add(point.vect);
        }
        return list;
    }

}
