package ru.vsu.cs.course2.graphics;

import java.awt.*;

public class WuLineDrawer implements LineDrawer {
    private PixelDrawer pixelDrawer;

    public WuLineDrawer(PixelDrawer pixelDrawer) {
        this.pixelDrawer = pixelDrawer;
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2, Color c) {
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
            drawWuPixel(x, y, err, dx, c, swap);
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

    private void drawWuPixel(int x, int y, int err, int dx, Color c, boolean swap) {
        if (!swap) {
            pixelDrawer.drawPixel(x, y - 1, new Color(c.getRed(), c.getGreen(), c.getBlue(), Math.max(0, (255 * err) / (-2 * dx))));
            pixelDrawer.drawPixel(x, y, new Color(c.getRed(), c.getGreen(), c.getBlue(), 255 - Math.max(0, (255 * err) / (-2 * dx))));
            pixelDrawer.drawPixel(x, y + 1, new Color(c.getRed(), c.getGreen(), c.getBlue(), Math.max(0, (255 * err) / (2 * dx))));
        } else {
            pixelDrawer.drawPixel(y - 1, x, new Color(c.getRed(), c.getGreen(), c.getBlue(), Math.max(0, (255 * err) / (-2 * dx))));
            pixelDrawer.drawPixel(y, x, new Color(c.getRed(), c.getGreen(), c.getBlue(), 255 - Math.max(0, (255 * err) / (-2 * dx))));
            pixelDrawer.drawPixel(y + 1, x, new Color(c.getRed(), c.getGreen(), c.getBlue(), Math.max(0, (255 * err) / (2 * dx))));
        }
    }
}
