package ru.vsu.cs.course2;

import ru.vsu.cs.course2.figures.Drawable;
import ru.vsu.cs.course2.figures.Figure;
import ru.vsu.cs.course2.modifiers.Modifier;

import java.util.List;

public class FigureBuilder {
    public static Drawable createFigure(FCContainer fc) {
        return createFigure(fc.getFc());
    }

    public static Drawable createFigure(FigureConfiguration fc) {
        Drawable drawable = new Figure(fc.getPlane());
        List<Modifier> modifiers = fc.getModifiers();
        if (modifiers != null)
            for (Modifier modifier : modifiers)
                drawable = modifier.build(drawable);
        return drawable;
    }
}
