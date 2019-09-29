package ru.vsu.cs.course2.graphics;

import java.awt.*;

public interface PieDrawer {
    void drawPie(Graphics2D graphics, int x1, int y1, int x2, int y2, double startAngle, double endAngle);
}
