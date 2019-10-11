package ru.vsu.cs.course2.graphics;

public class DDAEllipseDrawer implements EllipseDrawer {
    private GraphicsProvider graphicsProvider;

    public DDAEllipseDrawer(GraphicsProvider graphicsProvider) {
        this.graphicsProvider = graphicsProvider;
    }

    @Override
    public void drawEllipse(int x1, int y1, int x2, int y2) {
        drawArc(x1, y1, x2, y2, 0, 2 * Math.PI);
    }

    @Override
    public void drawArc(int x1, int y1, int x2, int y2, double startAngle, double endAngle) {
        if (x1 > x2) {
            int tmp = x1;
            x1 = x2;
            x2 = tmp;
        }
        if (y1 > y2) {
            int tmp = y1;
            y1 = y2;
            y2 = tmp;
        }

        int a = (x2 - x1) / 2;
        int b = (y2 - y1) / 2;

        int count = (int) ((Math.abs(x2 - x1) + Math.abs(y2 - y1)) * Math.abs(startAngle - endAngle) / Math.PI);
        double dt = (endAngle - startAngle) / count;

        double t = startAngle;
        PixelDrawer pixelDrawer = graphicsProvider.getPixelDrawer();
        for (int i = 0; i < count; i++) {
            int x = (int) (a * Math.cos(t));
            int y = (int) (b * Math.sin(t));
            pixelDrawer.drawPixel(x1 + x + a, y1 + y + b);
            t += dt;
        }
    }
}
