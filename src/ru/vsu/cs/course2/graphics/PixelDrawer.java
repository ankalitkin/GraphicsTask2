package ru.vsu.cs.course2.graphics;

import java.awt.*;

public interface PixelDrawer {
    void drawPixel(Graphics2D graphics, int x, int y);

    void drawPixel(Graphics2D graphics, int x, int y, int alpha);
}
