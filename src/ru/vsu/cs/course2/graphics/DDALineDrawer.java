package ru.vsu.cs.course2.graphics;

import java.awt.*;

public class DDALineDrawer implements LineDrawer {
    private PixelDrawer pixelDrawer;

    public DDALineDrawer(PixelDrawer pixelDrawer) {
        this.pixelDrawer = pixelDrawer;
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2, Color c) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        double D = Math.max(Math.abs(dx), Math.abs(dy));
        if (D < 1) {
            pixelDrawer.drawPixel(x1, y1, c);
            return;
        }
        double stepX = dx / D;
        double stepY = dy / D;
        double x = x1;
        double y = y1;
        for(int t = 0; t <= D; t++) {
            pixelDrawer.drawPixel((int)x, (int)y, c);
            x += stepX;
            y += stepY;
        }
    }
}
