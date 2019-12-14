package ru.vsu.cs.course2;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.awt.*;

@Data
public class FigureConfiguration {
    private static int counter = 0;
    private Plane plane = new Plane();
    private boolean isVisible = true;
    private boolean isClosed;
    private boolean isCurved;
    private boolean isStroked = true;
    private int strokeColorRGB = Color.black.getRGB();
    private int strokeThickness = 1;
    private boolean isFilled;
    private int fillColorRGB = Color.white.getRGB();
    private int number = ++counter;

    public static void setCounter(int counter) {
        FigureConfiguration.counter = counter;
    }

    @JsonIgnore
    public Color getStrokeColor() {
        return new Color(strokeColorRGB);
    }

    public void setStrokeColor(Color strokeColor) {
        this.strokeColorRGB = strokeColor.getRGB();
    }

    @JsonIgnore
    public Color getFillColor() {
        return new Color(fillColorRGB);
    }

    public void setFillColor(Color fillColor) {
        this.fillColorRGB = fillColor.getRGB();
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
        StringBuilder sb = new StringBuilder();
        sb.append("#");
        sb.append(number);
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
