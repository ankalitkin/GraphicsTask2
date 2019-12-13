package ru.vsu.cs.course2.figures;

import ru.vsu.cs.course2.Plane;
import ru.vsu.cs.course2.ScreenConverter;

import java.awt.*;
import java.util.List;

public class Figure implements Drawable{
    private Plane plane;

    public Figure(Plane plane) {
        this.plane = plane;
    }

    public Plane getPlane() {
        return plane;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    @Override
    public List<RealPoint> getOutlinePoints() {
        return plane.getPoints();
    }

    @Override
    public void draw(ScreenConverter screenConverter, Graphics2D graphics2D) {
        //Nothing to draw now
    }
}
