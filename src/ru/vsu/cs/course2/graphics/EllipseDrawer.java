package ru.vsu.cs.course2.graphics;

import java.awt.*;

public interface EllipseDrawer {
    void drawEllipse(Graphics2D graphics, int x1, int y1, int x2, int y2);

    void drawArc(Graphics2D graphics, int x1, int y1, int x2, int y2, double startAngle, double endAngle);
}
