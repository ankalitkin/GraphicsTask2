package ru.vsu.cs.course2.graphics;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BufferedImagePixelDrawer implements PixelDrawer {
    private GraphicsProvider graphicsProvider;

    public BufferedImagePixelDrawer(GraphicsProvider graphicsProvider) {
        this.graphicsProvider = graphicsProvider;
    }


    @Override
    public void drawPixel(int x, int y) {
        drawPixel(x, y, 255);
    }

    @Override
    public void drawPixel(int x, int y, int alpha) {
        Color color = graphicsProvider.getAlphaColor(alpha);
        BufferedImage image = graphicsProvider.getBufferedImage();
        if (0 <= x && x < image.getWidth()
                && 0 <= y && y < image.getHeight()) {
            image.setRGB(x, y, color.getRGB());
        }
    }

    public void fillColor(Color c) {
        int rgb = c.getRGB();
        BufferedImage image = graphicsProvider.getBufferedImage();
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                image.setRGB(i, j, rgb);
            }
        }
    }
}
