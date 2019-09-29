package ru.vsu.cs.course2.graphics;

import java.awt.*;

public class NativePixelDrawer implements PixelDrawer {
    private GraphicsProvider graphicsProvider;

    public NativePixelDrawer(GraphicsProvider graphicsProvider) {
        this.graphicsProvider = graphicsProvider;
    }

    @Override
    public void drawPixel(Graphics2D graphics, int x, int y) {
        graphics.setColor(graphicsProvider.getColor());
        graphics.drawLine(x, y, x, y);
    }

    @Override
    public void drawPixel(Graphics2D graphics, int x, int y, int alpha) {
        graphics.setColor(graphicsProvider.getAlphaColor(alpha));
        graphics.drawLine(x, y, x, y);
    }
}
