package ru.vsu.cs.course2;

import java.awt.*;

public class FigureConfiguration {
    private static int counter = 0;
    private Plane plane = new Plane();
    private boolean isVisible = true;
    private boolean isClosed;
    private boolean isCurved;
    private boolean isStroked = true;
    private Color strokeColor = Color.black;
    private int strokeThickness = 1;
    private boolean isFilled;
    private Color fillColor = Color.white;
    private int configNumber = ++counter;

    public static void setCounter(int counter) {
        FigureConfiguration.counter = counter;
    }

    public static FigureConfiguration getSampleFigureConfiguration() {
        FigureConfiguration fc = new FigureConfiguration();
        fc.setClosed(true);
        fc.setStrokeColor(Color.red);
        fc.setFilled(true);
        fc.setFillColor(Color.yellow);
        return fc;
    }

    public Plane getPlane() {
        return plane;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public boolean isCurved() {
        return isCurved;
    }

    public void setCurved(boolean curved) {
        isCurved = curved;
    }

    public boolean isStroked() {
        return isStroked;
    }

    public void setStroked(boolean stroked) {
        isStroked = stroked;
    }

    public Color getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(Color strokeColor) {
        this.strokeColor = strokeColor;
    }

    public int getStrokeThickness() {
        return strokeThickness;
    }

    public void setStrokeThickness(int strokeThickness) {
        this.strokeThickness = strokeThickness;
    }

    public boolean isFilled() {
        return isFilled;
    }

    public void setFilled(boolean filled) {
        isFilled = filled;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("#");
        sb.append(configNumber);
        sb.append(" ");
        if (isVisible)
            sb.append("Vis");
        if (isClosed)
            sb.append("Cls");
        if (isCurved)
            sb.append("Cur");
        if (isStroked)
            sb.append("Str");
        if (isFilled)
            sb.append("Fil");
        return sb.toString();
    }
}
