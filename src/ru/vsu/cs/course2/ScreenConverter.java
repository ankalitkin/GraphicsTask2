package ru.vsu.cs.course2;

public class ScreenConverter {
    private Editor editor;
    private double x0, y0;
    private double scale = 1;

    public ScreenConverter(Editor editor) {
        this.editor = editor;
    }

    public ScreenPoint realToScreen(Vector2 point) {
        double x = (point.getX() - x0) * scale + getScreenCenterX();
        double y = (y0 - point.getY()) * scale + getScreenCenterY();
        return new ScreenPoint((int) x, (int) y);
    }

    public Vector2 screenToReal(ScreenPoint p) {
        double x = x0 + (p.getX() - getScreenCenterX()) / scale;
        double y = y0 - (p.getY() - getScreenCenterY()) / scale;
        return new Vector2(x, y);
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
        return editor.getWidth() / 2;
    }

    public int getScreenCenterY() {
        return editor.getHeight() / 2;
    }
}
