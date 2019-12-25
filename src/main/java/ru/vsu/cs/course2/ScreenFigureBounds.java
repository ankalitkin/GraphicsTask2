package ru.vsu.cs.course2;

import lombok.Value;

@Value
public class ScreenFigureBounds {
    private int minX;
    private int minY;
    private int maxX;
    private int maxY;

    public boolean crossesWith(ScreenFigureBounds other) {
        return ((minX < other.maxX && minX > other.minX)
                || (maxX < other.maxX && maxX > other.minX))
                && ((minY < other.maxY && minY > other.minY)
                || (maxY < other.maxY && maxY > other.minY));
    }
}
