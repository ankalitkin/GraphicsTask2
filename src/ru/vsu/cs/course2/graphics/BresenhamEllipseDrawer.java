package ru.vsu.cs.course2.graphics;

import java.awt.*;

public class BresenhamEllipseDrawer implements EllipseDrawer {
    private GraphicsProvider graphicsProvider;

    public BresenhamEllipseDrawer(GraphicsProvider graphicsProvider) {
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
        int lastX = 0;
        for (int i = pos.length - 1; i >= 0; i--) {
            while (lastX <= pos[i]) {
                drawEllipsePixel(graphics, ellipse, lastX, i);
                if (i > 0 && pos[i - 1] == pos[i]) {
                    break;
                }
                lastX++;
            }
        }
    }

    private void drawEllipsePixel(Graphics2D graphics, Ellipse ellipse, int x, int y) {
        checkAndDrawPixel(graphics, ellipse, x, y);
        checkAndDrawPixel(graphics, ellipse, -x, y);
        checkAndDrawPixel(graphics, ellipse, -x, -y);
        checkAndDrawPixel(graphics, ellipse, x, -y);
    }

    private void checkAndDrawPixel(Graphics2D graphics, Ellipse ellipse, int x, int y) {
        if (BresenhamEllipseUtils.isPointInside(ellipse, x, y)) {
            graphicsProvider.getPixelDrawer().drawPixel(graphics, ellipse.getXc() + x, ellipse.getYc() + y);
        }
    }

}
