package ru.vsu.cs.course2;

import ru.vsu.cs.course2.figures.Drawable;
import ru.vsu.cs.course2.figures.Figure;
import ru.vsu.cs.course2.modifiers.Modifier;

public class FigureBuilder {
    public static Drawable createFigure(FCContainer fc) {
        return createFigure(fc.getFc());
    }

    public static Drawable createFigure(FigureConfiguration fc) {
        Drawable drawable = new Figure(fc.getPlane());
        for (Modifier modifier : fc.getModifiers())
            drawable = modifier.build(drawable);
        return drawable;
    }
}
