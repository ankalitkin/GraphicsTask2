package ru.vsu.cs.course2.graphics;

import java.awt.*;

public class DDALineDrawer implements LineDrawer {
    private GraphicsProvider graphicsProvider;

    public DDALineDrawer(GraphicsProvider graphicsProvider) {
        this.graphicsProvider = graphicsProvider;
    }

    @Override
    public void drawLine(Graphics2D graphics, int x1, int y1, int x2, int y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        double D = Math.max(Math.abs(dx), Math.abs(dy));
        PixelDrawer pixelDrawer = graphicsProvider.getPixelDrawer();
        if (D < 1) {
            pixelDrawer.drawPixel(graphics, x1, y1);
            return;
        }
        double stepX = dx / D;
        double stepY = dy / D;
        double x = x1;
        double y = y1;
        for(int t = 0; t <= D; t++) {
            pixelDrawer.drawPixel(graphics, (int) x, (int) y);
            x += stepX;
            y += stepY;
        }
    }
}
