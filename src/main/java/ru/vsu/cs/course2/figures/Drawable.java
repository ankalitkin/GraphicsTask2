package ru.vsu.cs.course2.figures;

import ru.vsu.cs.course2.ScreenConverter;

import java.awt.*;
import java.util.List;

public interface Drawable {
    List<RealPoint> getOutlinePoints();

    void draw(ScreenConverter screenConverter, Graphics2D graphics2D);

    default Object getSyncObject() {
        return new Object();
    }
}
