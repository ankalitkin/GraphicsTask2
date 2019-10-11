package ru.vsu.cs.course2.graphics;

public class BresenhamEllipseDrawer implements EllipseDrawer {
    private GraphicsProvider graphicsProvider;

    public BresenhamEllipseDrawer(GraphicsProvider graphicsProvider) {
        this.graphicsProvider = graphicsProvider;
    }

    @Override
    public void drawEllipse(int x1, int y1, int x2, int y2) {
        drawArc(x1, y1, x2, y2, 0, 2 * Math.PI);
    }

    @Override
    public void drawArc(int x1, int y1, int x2, int y2, double startAngle, double endAngle) {
        Ellipse ellipse = new Ellipse(x1, y1, x2, y2, startAngle, endAngle);
        drawEllipse(ellipse);
    }

    private void drawEllipse(Ellipse ellipse) {
        int[] pos = ellipse.getProcessed();
        int lastX = 0;
        for (int i = pos.length - 1; i >= 0; i--) {
            while (lastX <= pos[i]) {
                drawEllipsePixel(ellipse, lastX, i);
                if (i > 0 && pos[i - 1] == pos[i]) {
                    break;
                }
                lastX++;
            }
        }
    }

    private void drawEllipsePixel(Ellipse ellipse, int x, int y) {
        checkAndDrawPixel(ellipse, x, y);
        checkAndDrawPixel(ellipse, -x, y);
        checkAndDrawPixel(ellipse, -x, -y);
        checkAndDrawPixel(ellipse, x, -y);
    }

    private void checkAndDrawPixel(Ellipse ellipse, int x, int y) {
        if (BresenhamEllipseUtils.isPointInside(ellipse, x, y)) {
            graphicsProvider.getPixelDrawer().drawPixel(ellipse.getXc() + x, ellipse.getYc() + y);
        }
    }

}
