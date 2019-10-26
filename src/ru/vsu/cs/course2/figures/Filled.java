package ru.vsu.cs.course2.figures;

import ru.vsu.cs.course2.ScreenConverter;
import ru.vsu.cs.course2.ScreenPoint;

import java.awt.*;
import java.util.List;

public class Filled implements Drawable {
    private static final Color TRANSPARENT = new Color(0, 0, 0, 0);
    private Drawable drawable;
    private Color color;

    public Filled(Drawable drawable, Color color) {
        this.drawable = drawable;
        this.color = color;
    }

    @Override
    public List<RealPoint> getOutlinePoints() {
        return drawable.getOutlinePoints();
    }

    @Override
    public void draw(ScreenConverter screenConverter, Graphics2D graphics2D) {
        List<RealPoint> points = drawable.getOutlinePoints();
        if (points.size() < 3)
            return;

        graphics2D.setXORMode(Color.white);
        graphics2D.setColor(color);
        ScreenPoint origin = screenConverter.getCenter();
        ScreenPoint lastScreenPoint = screenConverter.realToScreen(points.get(points.size() - 1));
        for (RealPoint point : points) {
            ScreenPoint currentScreenPoint = screenConverter.realToScreen(point);
            //if(lastScreenPoint != null)
            drawTriangle(graphics2D, origin, lastScreenPoint, currentScreenPoint);
            lastScreenPoint = currentScreenPoint;
        }

        graphics2D.setPaintMode();
        drawable.draw(screenConverter, graphics2D);
    }

    private void drawTriangle(Graphics2D graphics2D, ScreenPoint origin, ScreenPoint lastScreenPoint, ScreenPoint currentScreenPoint) {
        int[] xPoints = {origin.getX(), lastScreenPoint.getX(), currentScreenPoint.getX()};
        int[] yPoints = {origin.getY(), lastScreenPoint.getY(), currentScreenPoint.getY()};
        graphics2D.fillPolygon(xPoints, yPoints, 3);
    }
}