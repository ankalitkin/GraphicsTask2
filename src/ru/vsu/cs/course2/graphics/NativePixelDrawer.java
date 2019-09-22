package ru.vsu.cs.course2.graphics;

import java.awt.*;

public class NativePixelDrawer implements PixelDrawer {
    private Graphics2D graphics;

    public NativePixelDrawer(Graphics2D graphics) {
        this.graphics = graphics;
    }

    @Override
    public void drawPixel(int x, int y, Color c) {
        graphics.setColor(c);
        graphics.drawLine(x, y, x, y);
    }
}
