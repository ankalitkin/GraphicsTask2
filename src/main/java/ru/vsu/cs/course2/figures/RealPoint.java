package ru.vsu.cs.course2.figures;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RealPoint {
    private double x, y;

    public RealPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @JsonSetter
    private void setX(double x) {
        this.x = x;
    }

    @JsonSetter
    private void setY(double y) {
        this.y = y;
    }
}
