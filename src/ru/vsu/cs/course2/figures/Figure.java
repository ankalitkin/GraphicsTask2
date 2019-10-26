package ru.vsu.cs.course2.figures;

import ru.vsu.cs.course2.ScreenConverter;

import java.awt.*;
import java.util.List;

public class Figure implements Drawable{
    private List<RealPoint> points;
    private int z_index;

    public Figure(List<RealPoint> points, int z_index) {
        this.points = points;
        this.z_index = z_index;
    }

    public List<RealPoint> getPoints() {
        return points;
    }

    public void setPoints(List<RealPoint> points) {
        this.points = points;
    }

    public int getZ_index() {
        return z_index;
    }

    public void setZ_index(int z_index) {
        this.z_index = z_index;
    }

    @Override
    public List<RealPoint> getOutlinePoints() {
        return getPoints();
    }

    @Override
    public void draw(ScreenConverter screenConverter, Graphics2D graphics2D) {
        //Nothing to draw now
    }
}
