package ru.vsu.cs.course2;

import lombok.Value;

@Value
public class ScreenFigureBounds {
    private int minX;
    private int maxX;
    private int minY;
    private int maxY;

    public boolean contains(ScreenFigureBounds other) {
        int x1 = other.minX;
        int x2 = other.maxX;
        int y1 = other.minY;
        int y2 = other.maxY;
        boolean xCross = (minX <= x1 && x1 <= maxX) || (minX <= x2 && x2 <= maxX);
        boolean yCross = (minY <= y1 && y1 <= maxY) || (minY <= y2 && y2 <= maxY);
        return xCross && yCross;
    }

}
