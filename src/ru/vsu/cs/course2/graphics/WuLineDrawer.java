package ru.vsu.cs.course2.graphics;

import java.awt.*;

public class WuLineDrawer implements LineDrawer {
    private PixelDrawer pixelDrawer;
    private static Color cachedColor;
    private static Color[] alphaColor;
    
    public WuLineDrawer(PixelDrawer pixelDrawer) {
        this.pixelDrawer = pixelDrawer;
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2, Color c) {
        cacheColors(c);

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
            drawWuPixel(x, y, err, dx, swap);
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

    private void drawWuPixel(int x, int y, int err, int dx, boolean swap) {
        int d = (255 * err) / (2 * dx);
        int dPos = Math.max(0, d);
        int dNeg = Math.max(0, -d);
        if (!swap) {
            pixelDrawer.drawPixel(x, y - 1, alphaColor[dNeg]);
            pixelDrawer.drawPixel(x, y, alphaColor[255 - dNeg]);
            pixelDrawer.drawPixel(x, y + 1, alphaColor[dPos]);
        } else {
            pixelDrawer.drawPixel(y - 1, x, alphaColor[dNeg]);
            pixelDrawer.drawPixel(y, x, alphaColor[255 - dNeg]);
            pixelDrawer.drawPixel(y + 1, x, alphaColor[dPos]);
        }
    }
    
    private void cacheColors(Color color) {
        if (color.equals(cachedColor))
            return;
        cachedColor = color;
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        float alpha = color.getRGBComponents(null)[3];
        alphaColor = new Color[256];
        for(int i = 0; i < 256; i++)
            alphaColor[i] = new Color(r, g, b, (int)(i * alpha));
    }
}
