package ru.vsu.cs.course2.figures;

import ru.vsu.cs.course2.Plane;
import ru.vsu.cs.course2.ScreenConverter;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Closed implements Drawable {
    private Drawable drawable;

    public Closed(Drawable drawable) {
        this.drawable = drawable;
    }

    @Override
    public List<RealPoint> getOutlinePoints() {
        List<RealPoint> outlinePoints = drawable.getOutlinePoints();
        if (outlinePoints.size() == 0)
            return Collections.emptyList();
        List<RealPoint> newList = new ArrayList<>(outlinePoints.size()+1);
        newList.addAll(outlinePoints);
        newList.add(outlinePoints.get(0));
        return newList;
    }

    @Override
    public void draw(ScreenConverter screenConverter, Graphics2D graphics2D) {
        drawable.draw(screenConverter, graphics2D);
    }
}
