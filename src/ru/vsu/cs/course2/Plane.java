package ru.vsu.cs.course2;

import ru.vsu.cs.course2.graphics.GraphicsProvider;

import java.util.ArrayList;
import java.util.List;

class Plane {
    class Point {
        Vector2 vect;
        int z;

        Point(Vector2 vect, int z) {
            this.vect = vect;
            this.z = z;
        }
    }

    private GraphicsProvider graphicsProvider;
    ArrayList<Point> points = new ArrayList<>();
    private int z;

    public Plane(GraphicsProvider graphicsProvider) {
        this.graphicsProvider = graphicsProvider;
    }

    public Point newPoint(int x, int y, int z) {
        Vector2 realPoint = VectorFromScreen(x, y);
        return new Point(realPoint, z);
    }

    public Vector2 VectorFromScreen(int x, int y) {
        ScreenPoint screenPoint = new ScreenPoint(x, y);
        return graphicsProvider.getScreenConverter().screenToReal(screenPoint);
    }


    int nextZ() {
        return z++;
    }

    Point getClosest(int x, int y, int dx, int dy) {
        Point closest = null;
        for (Point point : points) {
            if (Math.abs(x - getX(point)) < dx
                    && Math.abs(y - getY(point)) < dy
                    && (closest == null || point.z > closest.z)) {
                closest = point;
            }
        }
        return closest;
    }

    Point addNewPoint(int x, int y) {
        Point p = newPoint(x, y, nextZ());
        points.add(p);
        return p;
    }

    private int getX(Point point) {
        return graphicsProvider.getScreenConverter().realToScreen(point.vect).x;
    }

    private int getY(Point point) {
        return graphicsProvider.getScreenConverter().realToScreen(point.vect).y;
    }

    public List<Vector2> getPoints() {
        ArrayList<Vector2> list = new ArrayList<>(points.size());
        for (Point point : points) {
            list.add(point.vect);
        }
        return list;
    }

}
