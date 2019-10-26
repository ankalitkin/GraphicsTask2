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

        ScreenPoint last = null;
        for(RealPoint outlinePoint : drawable.getOutlinePoints()) {
            ScreenPoint point = screenConverter.realToScreen(outlinePoint);
            if (last != null)
                graphics2D.drawLine(last.getX(), last.getY(), point.getX(), point.getY());
            last = point;
        }
    }
}
