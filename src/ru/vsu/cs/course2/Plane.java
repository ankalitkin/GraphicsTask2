package ru.vsu.cs.course2;

import java.util.ArrayList;

class Plane {
    class Point {
        int x, y, z;

        Point(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    ArrayList<Point> points = new ArrayList<>();
    private int z;

    int nextZ() {
        return z++;
    }

    Point getClosest(int x, int y, int dx, int dy) {
        Point closest = null;
        for (Point point : points) {
            if (Math.abs(x - point.x) < dx
                    && Math.abs(y - point.y) < dy
                    && (closest == null || point.x > closest.z)) {
                closest = point;
            }
        }
        return closest;
    }

    Point addNewPoint(int x, int y) {
        Point p = new Point(x, y, nextZ());
        points.add(p);
        return p;
    }

}
