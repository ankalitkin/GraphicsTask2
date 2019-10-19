package ru.vsu.cs.course2.graphics;

import ru.vsu.cs.course2.ScreenConverter;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GraphicsProvider {
    private Color color;
    private Color[] alphaColors;
    private PixelDrawer pixelDrawer;
    private LineDrawer lineDrawer;
    private Graphics2D graphics;
    private BufferedImage bufferedImage;
    private ScreenConverter screenConverter;
    private PolyLineDrawer polyLineDrawer;
    private RealLineDrawer realLineDrawer;
    private CurveDrawer curveDrawer;

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


    public Graphics2D getGraphics() {
        return graphics;
    }

    public void setGraphics(Graphics2D graphics) {
        this.graphics = graphics;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    public ScreenConverter getScreenConverter() {
        return screenConverter;
    }

    public void setScreenConverter(ScreenConverter screenConverter) {
        this.screenConverter = screenConverter;
    }

    public PolyLineDrawer getPolyLineDrawer() {
        return polyLineDrawer;
    }

    public void setPolyLineDrawer(PolyLineDrawer polyLineDrawer) {
        this.polyLineDrawer = polyLineDrawer;
    }

    public RealLineDrawer getRealLineDrawer() {
        return realLineDrawer;
    }

    public void setRealLineDrawer(RealLineDrawer realLineDrawer) {
        this.realLineDrawer = realLineDrawer;
    }

    public CurveDrawer getCurveDrawer() {
        return curveDrawer;
    }

    public void setCurveDrawer(CurveDrawer curveDrawer) {
        this.curveDrawer = curveDrawer;
    }
}
