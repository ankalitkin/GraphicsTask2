package ru.vsu.cs.course2.graphics;

import java.awt.*;

public class BresenhamLineDrawer implements LineDrawer {
    private PixelDrawer pixelDrawer;

    public BresenhamLineDrawer(PixelDrawer pixelDrawer) {
        this.pixelDrawer = pixelDrawer;
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2, Color c) {
        int x, y, dx, dy;
        boolean swap = false;

        if (y2 - y1 < x2 - x1) {
            x = x1;
            y = y1;
            dx = x2 - x1;
            dy = y2 - y1;
        } else {
            x = y1;
            y = x1;
            dx = y2 - y1;
            dy = x2 - x1;
            swap = true;
        }

        
        int err = 0;
        for (int i = 0; i <= dx; i++) {
            if (swap)
                pixelDrawer.drawPixel(y, x, c);
            else
                pixelDrawer.drawPixel(x, y, c);
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
