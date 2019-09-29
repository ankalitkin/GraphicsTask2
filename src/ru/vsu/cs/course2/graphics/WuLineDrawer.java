package ru.vsu.cs.course2.graphics;

import java.awt.*;

public class WuLineDrawer implements LineDrawer {
    private GraphicsProvider graphicsProvider;

    public WuLineDrawer(GraphicsProvider graphicsProvider) {
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

        int err = 0;
        for (int i = 0; i <= dx; i++) {
            drawWuPixel(graphics, x, y, err, dx, swap);
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

    private void drawWuPixel(Graphics2D graphics, int x, int y, int err, int dx, boolean swap) {
        int d = dx != 0 ? (255 * err) / (2 * dx) : 255;
        int dPos = Math.max(0, d);
        int dNeg = Math.max(0, -d);
        int dMax = 255 - Math.abs(d);
        PixelDrawer pixelDrawer = graphicsProvider.getPixelDrawer();
        if (!swap) {
            pixelDrawer.drawPixel(graphics, x, y, dMax);
            if (dx != 0) {
                if (dPos > 0)
                    pixelDrawer.drawPixel(graphics, x, y + 1, dPos);
                else
                    pixelDrawer.drawPixel(graphics, x, y - 1, dNeg);
            }
        } else {
            pixelDrawer.drawPixel(graphics, y, x, dMax);
            if (dx != 0) {
                if (dPos > 0)
                    pixelDrawer.drawPixel(graphics, y + 1, x, dPos);
                else
                    pixelDrawer.drawPixel(graphics, y - 1, x, dNeg);
            }
        }
    }

}
