package ru.vsu.cs.course2.graphics;

public interface EllipseDrawer {
    void drawEllipse(int x1, int y1, int x2, int y2);

    void drawArc(int x1, int y1, int x2, int y2, double startAngle, double endAngle);
}
