package ru.vsu.cs.course2.figures;

import ru.vsu.cs.course2.ScreenConverter;
import ru.vsu.cs.course2.ScreenPoint;

import java.awt.*;
import java.util.List;

public class Stroke implements Drawable{
    private Drawable drawable;
    private Color color;
    private double thickness;

    public Stroke(Drawable drawable, Color color, double thickness) {
        this.drawable = drawable;
        this.color = color;
        this.thickness = thickness;
    }

    public Color getColor() {
        return color;
    }

    public double getThickness() {
        return thickness;
    }

    @Override
    public List<RealPoint> getOutlinePoints() {
        return drawable.getOutlinePoints();
    }

    @Override
    public void draw(ScreenConverter screenConverter, Graphics2D graphics2D) {
        graphics2D.setColor(color);
        List<RealPoint> points = drawable.getOutlinePoints();
        int[] xPoints = new int[points.size()];
        int[] yPoints = new int[points.size()];
        int i = 0;
        for (RealPoint outlinePoint : points) {
            ScreenPoint point = screenConverter.realToScreen(outlinePoint);
            xPoints[i] = point.getX();
            yPoints[i] = point.getY();
            i++;
        }
        graphics2D.setStroke(new BasicStroke((float) (screenConverter.getScale() * thickness)));
        graphics2D.drawPolygon(new Polygon(xPoints, yPoints, points.size()));
    }
}
