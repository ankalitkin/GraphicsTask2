package ru.vsu.cs.course2.figures;

import ru.vsu.cs.course2.Plane;
import ru.vsu.cs.course2.ScreenConverter;
import ru.vsu.cs.course2.ScreenPoint;

import java.awt.*;
import java.util.List;

public class Filled implements Drawable {
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
        if (points.size() >= 3) {
            graphics2D.setColor(color);
            int[] xPoints = new int[points.size()];
            int[] yPoints = new int[points.size()];
            int i = 0;
            for (RealPoint outlinePoint : points) {
                ScreenPoint point = screenConverter.realToScreen(outlinePoint);
                xPoints[i] = point.getX();
                yPoints[i] = point.getY();
                i++;
            }
            graphics2D.fillPolygon(xPoints, yPoints, points.size());
        }
        drawable.draw(screenConverter, graphics2D);
    }

    @Override
    public Plane getPlane() {
        return drawable.getPlane();
    }
}