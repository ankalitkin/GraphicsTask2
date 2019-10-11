package ru.vsu.cs.course2.graphics;

import java.awt.*;

public class GraphicsPixelDrawer implements PixelDrawer {
    private GraphicsProvider graphicsProvider;

    public GraphicsPixelDrawer(GraphicsProvider graphicsProvider) {
        this.graphicsProvider = graphicsProvider;
    }

    @Override
    public void drawPixel(int x, int y) {
        Graphics2D graphics = graphicsProvider.getGraphics();
        graphics.setColor(graphicsProvider.getColor());
        graphics.drawLine(x, y, x, y);
    }

    @Override
    public void drawPixel(int x, int y, int alpha) {
        Graphics2D graphics = graphicsProvider.getGraphics();
        graphics.setColor(graphicsProvider.getAlphaColor(alpha));
        graphics.drawLine(x, y, x, y);
    }
}
