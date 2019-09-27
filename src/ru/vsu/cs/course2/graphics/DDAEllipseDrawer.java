package ru.vsu.cs.course2.graphics;

import java.awt.*;

public class DDAEllipseDrawer implements LineDrawer {
    private PixelDrawer pixelDrawer;

    public DDAEllipseDrawer(PixelDrawer pixelDrawer) {
        this.pixelDrawer = pixelDrawer;
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2, Color c) {
        if (x1 > x2) {
            int tmp = x1;
            x1 = x2;
            x2 = tmp;
        }
        if (y1 > y2) {
            int tmp = y1;
            y1 = y2;
            y2 = tmp;
        }

        int a = (x2 - x1) / 2;
        int b = (y2 - y1) / 2;

        int count = 2 * (x2 - x1) + 2 * (y2 - y1);
        double dt = (2 * Math.PI) / count;

        double t = 0;
        for (int i = 0; i < count; i++) {
            int x = (int) (a * Math.cos(t));
            int y = (int) (b * Math.sin(t));
            pixelDrawer.drawPixel(x1 + x + a, y1 + y + b, c);
            t += dt;
        }

    }
}
