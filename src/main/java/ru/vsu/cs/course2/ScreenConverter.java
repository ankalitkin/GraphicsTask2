package ru.vsu.cs.course2;

import ru.vsu.cs.course2.figures.RealPoint;

import javax.swing.*;
import java.util.List;

public class ScreenConverter {
    private JPanel parent;
    private double x0, y0;
    private double scale = 1;

    public ScreenConverter(JPanel parent) {
        this.parent = parent;
    }

    public ScreenPoint realToScreen(RealPoint point) {
        double x = (point.getX() - x0) * scale + getScreenCenterX();
        double y = (y0 - point.getY()) * scale + getScreenCenterY();
        return new ScreenPoint((int) x, (int) y);
    }

    public RealPoint screenToReal(ScreenPoint p) {
        double x = x0 + (p.getX() - getScreenCenterX()) / scale;
        double y = y0 - (p.getY() - getScreenCenterY()) / scale;
        return new RealPoint(x, y);
    }

    public void translate(double x, double y) {
        x0 += x;
        y0 += y;
    }

    public void translateOnScreen(double x, double y) {
        translate(x / scale, -y / scale);
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public void scale(java.awt.Point pointer, double amount) {
        double ds = scale * amount;
        double dx = (getScreenCenterX() - pointer.x) * ds;
        double dy = (getScreenCenterY() - pointer.y) * ds;
        translateOnScreen(-dx, -dy);
        scale += ds;
    }

    public int getScreenCenterX() {
        return parent.getWidth() / 2;
    }

    public int getScreenCenterY() {
        return parent.getHeight() / 2;
    }

    public ScreenPoint getCenter() {
        return new ScreenPoint(getScreenCenterX(), getScreenCenterY());
    }

    public ScreenFigureBounds getBounds(List<RealPoint> list) {
        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;
        for (RealPoint rp : list) {
            ScreenPoint sp = realToScreen(rp);
            if (sp.getX() < minX)
                minX = sp.getX();
            if (sp.getX() > maxX)
                maxX = sp.getX();
            if (sp.getY() < minY)
                minY = sp.getY();
            if (sp.getY() > maxY)
                maxY = sp.getY();
        }
        return new ScreenFigureBounds(minX, maxX, minY, maxY);
    }
}
