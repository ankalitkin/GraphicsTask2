package ru.vsu.cs.course2;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.awt.*;

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
    private int number = ++counter;

    public static void setCounter(int counter) {
        FigureConfiguration.counter = counter;
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

}
