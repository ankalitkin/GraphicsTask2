package ru.vsu.cs.course2.graphics;

import java.awt.*;

public class PieDrawingWrapper implements PieDrawer {
    private GraphicsProvider graphicsProvider;
    public static final double EPS = 0.001;

    public PieDrawingWrapper(GraphicsProvider graphicsProvider) {
        this.graphicsProvider = graphicsProvider;
    }

    @Override
    public void drawPie(Graphics2D graphics, int x1, int y1, int x2, int y2, double startAngle, double endAngle) {
        LineDrawer lineDrawer = graphicsProvider.getLineDrawer();
        EllipseDrawer ellipseDrawer = graphicsProvider.getEllipseDrawer();
        int xc = (x1 + x2) / 2;
        int yc = (y1 + y2) / 2;
        int a = (x2 - x1) / 2;
        int b = (y2 - y1) / 2;
        int xs = (int) (a * Math.cos(startAngle));
        int ys = (int) (b * Math.sin(startAngle));
        int xe = (int) (a * Math.cos(endAngle));
        int ye = (int) (b * Math.sin(endAngle));
        ellipseDrawer.drawEllipse(graphics, x1, y1, x2, y2, startAngle, endAngle);

        double val = Math.abs((startAngle - endAngle) % (Math.PI * 2));
        if (val >= EPS && val <= Math.PI * 2 - EPS) {
            lineDrawer.drawLine(graphics, xc, yc, xc + xs, yc + ys);
            lineDrawer.drawLine(graphics, xc, yc, xc + xe, yc + ye);
        }
    }
}
