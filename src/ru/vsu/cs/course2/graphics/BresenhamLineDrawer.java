package ru.vsu.cs.course2.graphics;

import java.awt.*;

public class BresenhamLineDrawer implements LineDrawer {
    private GraphicsProvider graphicsProvider;

    public BresenhamLineDrawer(GraphicsProvider graphicsProvider) {
        this.graphicsProvider = graphicsProvider;
    }

    @Override
    public void drawLine(Graphics2D graphics, int x1, int y1, int x2, int y2) {
        int x, y, dx, dy;
        boolean swap = false;

        if (Math.abs(y2 - y1) < Math.abs(x2 - x1)) {
            if (0 < x2 - x1) {
                x = x1;
                y = y1;
                dx = x2 - x1;
                dy = y2 - y1;
            } else {
                x = x2;
                y = y2;
                dx = x1 - x2;
                dy = y1 - y2;
            }
        } else {
            swap = true;
            if (0 < y2 - y1) {
                x = y1;
                y = x1;
                dx = y2 - y1;
                dy = x2 - x1;
            } else {
                x = y2;
                y = x2;
                dx = y1 - y2;
                dy = x1 - x2;
            }
        }

        PixelDrawer pixelDrawer = graphicsProvider.getPixelDrawer();
        int err = 0;
        for (int i = 0; i <= dx; i++) {
            if (swap)
                pixelDrawer.drawPixel(graphics, y, x);
            else
                pixelDrawer.drawPixel(graphics, x, y);
            err += 2 * dy;
            if (err > dx) {
                err -= 2 * dx;
                y++;
            } else if (err < -dx) {
                err += 2 * dx;
                y--;
            }
            x++;
        }
    }
}
