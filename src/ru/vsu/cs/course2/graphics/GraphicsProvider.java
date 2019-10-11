package ru.vsu.cs.course2.graphics;

import java.awt.*;

public class GraphicsProvider {
    private Color color;
    private Color[] alphaColors;
    private PixelDrawer pixelDrawer;
    private LineDrawer lineDrawer;
    private EllipseDrawer ellipseDrawer;
    private PieDrawer pieDrawer;
    private Graphics2D graphics;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        if (color.equals(this.color))
            return;
        this.color = color;
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        float alpha = color.getRGBComponents(null)[3];
        alphaColors = new Color[256];
        for (int i = 0; i < 256; i++)
            alphaColors[i] = new Color(r, g, b, (int) (i * alpha));

    }

    public Color getAlphaColor(int alpha) {
        return alphaColors[alpha];
    }

    public PixelDrawer getPixelDrawer() {
        return pixelDrawer;
    }

    public void setPixelDrawer(PixelDrawer pixelDrawer) {
        this.pixelDrawer = pixelDrawer;
    }

    public LineDrawer getLineDrawer() {
        return lineDrawer;
    }

    public void setLineDrawer(LineDrawer lineDrawer) {
        this.lineDrawer = lineDrawer;
    }

    public EllipseDrawer getEllipseDrawer() {
        return ellipseDrawer;
    }

    public void setEllipseDrawer(EllipseDrawer ellipseDrawer) {
        this.ellipseDrawer = ellipseDrawer;
    }

    public PieDrawer getPieDrawer() {
        return pieDrawer;
    }

    public void setPieDrawer(PieDrawer pieDrawer) {
        this.pieDrawer = pieDrawer;
    }

    public Graphics2D getGraphics() {
        return graphics;
    }

    public void setGraphics(Graphics2D graphics) {
        this.graphics = graphics;
    }
}
