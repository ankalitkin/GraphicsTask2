package ru.vsu.cs.course2;

import java.awt.*;

public class FigureConfiguration {
    private boolean isClosed;
    private boolean isCurved;
    private boolean isStroked;
    private Color strokeColor;
    private int strokeThickness;
    private boolean isFilled;
    private Color fillColor;

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
}
