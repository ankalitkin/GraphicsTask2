package ru.vsu.cs.course2;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vsu.cs.course2.figures.RealPoint;

@Data
@NoArgsConstructor
public class Point {
    RealPoint vect;
    int z;

    Point(RealPoint vect, int z) {
        this.vect = vect;
        this.z = z;
    }
}
