package ru.vsu.cs.course2.graphics;

import java.awt.*;

public class BresenhamFilledEllipseDrawer implements EllipseDrawer {
    private GraphicsProvider graphicsProvider;

    public BresenhamFilledEllipseDrawer(GraphicsProvider graphicsProvider) {
        this.graphicsProvider = graphicsProvider;
    }

    @Override
    public void drawEllipse(Graphics2D graphics, int x1, int y1, int x2, int y2) {
        drawArc(graphics, x1, y1, x2, y2, 0, 2 * Math.PI);
    }

    @Override
    public void drawArc(Graphics2D graphics, int x1, int y1, int x2, int y2, double startAngle, double endAngle) {
        Ellipse ellipse = new Ellipse(x1, y1, x2, y2, startAngle, endAngle);
        drawEllipse(graphics, ellipse);
    }

    private void drawEllipse(Graphics2D graphics, Ellipse ellipse) {
        int[] pos = ellipse.getProcessed();
        for (int y = 0; y < pos.length; y++) {
            for (int x = -pos[y]; x <= pos[y]; x++) {
                checkAndDrawPixel(graphics, ellipse, x, y);
                checkAndDrawPixel(graphics, ellipse, x, -y);
            }
        }
    }

    private void checkAndDrawPixel(Graphics2D graphics, Ellipse ellipse, int x, int y) {
        if (BresenhamEllipseUtils.isPointInside(ellipse, x, y)) {
            graphicsProvider.getPixelDrawer().drawPixel(graphics, ellipse.getXc() + x, ellipse.getYc() + y);
        }
    }
}
