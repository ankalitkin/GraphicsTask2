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

    public RealPoint clone() {
        return new RealPoint(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RealPoint realPoint = (RealPoint) o;

        if (Double.compare(realPoint.x, x) != 0) return false;
        return Double.compare(realPoint.y, y) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
