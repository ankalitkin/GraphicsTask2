package ru.vsu.cs.course2;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vsu.cs.course2.figures.RealPoint;

import java.util.Objects;

@Data
@NoArgsConstructor
public class Point {
    RealPoint vect;
    int z;

    Point(RealPoint vect, int z) {
        this.vect = vect;
        this.z = z;
    }

    public Point clone() {
        return new Point(vect.clone(), z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (z != point.z) return false;
        return Objects.equals(vect, point.vect);

    }

    @Override
    public int hashCode() {
        int result = vect != null ? vect.hashCode() : 0;
        result = 31 * result + z;
        return result;
    }
}
