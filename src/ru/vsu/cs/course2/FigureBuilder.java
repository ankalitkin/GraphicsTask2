package ru.vsu.cs.course2;

import ru.vsu.cs.course2.figures.*;

public class FigureBuilder {
    public static Drawable createFigure(FigureConfiguration fc) {
        Plane plane = new Plane();
        Drawable drawable = new Figure(plane);
        if (fc.isCurved()) {
            if (fc.isClosed())
                drawable = new ClosedCurved(drawable);
            else
                drawable = new Curved(drawable);
        } else {
            if (fc.isClosed())
                drawable = new Closed(drawable);
        }
        if (fc.isStroked())
            drawable = new Stroke(drawable, fc.getStrokeColor(), fc.getStrokeThickness());
        if (fc.isFilled())
            drawable = new Filled(drawable, fc.getFillColor());
        return drawable;
    }
}
