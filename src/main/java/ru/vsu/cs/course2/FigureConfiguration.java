package ru.vsu.cs.course2;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.awt.*;
import java.util.Arrays;
import java.util.Objects;

@Data
public class FigureConfiguration {
    private static int counter = 0;
    private Plane plane = new Plane();
    private String name = "";
    private boolean isVisible = true;
    private boolean isClosed;
    private boolean isCurved;
    private boolean isStroked = true;
    private float[] strokeRGBA = Color.black.getRGBComponents(null);
    private int strokeThickness = 1;
    private boolean isFilled;
    private float[] fillRGBA = Color.white.getRGBComponents(null);
    private int number;

    public static void setCounter(int counter) {
        FigureConfiguration.counter = counter;
    }

    public FigureConfiguration() {
        this(true);
    }

    private FigureConfiguration(boolean q) {
        if (q)
            number = ++counter;
        else
            number = counter;
    }

    @JsonIgnore
    public Color getStrokeColor() {
        return new Color(strokeRGBA[0], strokeRGBA[1], strokeRGBA[2], strokeRGBA[3]);
    }

    public void setStrokeColor(Color strokeColor) {
        this.strokeRGBA = strokeColor.getComponents(null);
    }

    @JsonIgnore
    public Color getFillColor() {
        return new Color(fillRGBA[0], fillRGBA[1], fillRGBA[2], fillRGBA[3]);
    }

    public void setFillColor(Color fillColor) {
        this.fillRGBA = fillColor.getComponents(null);
    }

    public static FigureConfiguration getSampleFigureConfiguration() {
        FigureConfiguration fc = new FigureConfiguration();
        fc.setClosed(true);
        fc.setStrokeColor(Color.red);
        fc.setFilled(true);
        fc.setFillColor(Color.yellow);
        return fc;
    }

    @Override
    public String toString() {
        if (name.length() == 0)
            return String.format("Figure %d", number);
        return name;
    }

    public FigureConfiguration clone() {
        FigureConfiguration fc = new FigureConfiguration(false);
        fc.setPlane(plane.clone());
        fc.setName(name);
        fc.setVisible(isVisible);
        fc.setClosed(isClosed);
        fc.setCurved(isCurved);
        fc.setStroked(isStroked);
        fc.setStrokeRGBA(strokeRGBA);
        fc.setStrokeThickness(strokeThickness);
        fc.setFilled(isFilled);
        fc.setFillRGBA(fillRGBA);
        fc.setNumber(number);
        return fc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FigureConfiguration that = (FigureConfiguration) o;

        if (isVisible != that.isVisible) return false;
        if (isClosed != that.isClosed) return false;
        if (isCurved != that.isCurved) return false;
        if (isStroked != that.isStroked) return false;
        if (strokeThickness != that.strokeThickness) return false;
        if (isFilled != that.isFilled) return false;
        if (number != that.number) return false;
        if (!Objects.equals(plane, that.plane)) return false;
        if (!Objects.equals(name, that.name)) return false;
        if (!Arrays.equals(strokeRGBA, that.strokeRGBA)) return false;
        return Arrays.equals(fillRGBA, that.fillRGBA);

    }

    @Override
    public int hashCode() {
        int result = plane != null ? plane.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (isVisible ? 1 : 0);
        result = 31 * result + (isClosed ? 1 : 0);
        result = 31 * result + (isCurved ? 1 : 0);
        result = 31 * result + (isStroked ? 1 : 0);
        result = 31 * result + Arrays.hashCode(strokeRGBA);
        result = 31 * result + strokeThickness;
        result = 31 * result + (isFilled ? 1 : 0);
        result = 31 * result + Arrays.hashCode(fillRGBA);
        result = 31 * result + number;
        return result;
    }
}
