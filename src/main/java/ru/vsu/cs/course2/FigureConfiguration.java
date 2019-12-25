package ru.vsu.cs.course2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vsu.cs.course2.modifiers.FilledModifier;
import ru.vsu.cs.course2.modifiers.Modifier;
import ru.vsu.cs.course2.modifiers.OutlineModifier;
import ru.vsu.cs.course2.modifiers.StrokedModifier;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FigureConfiguration {
    private static int counter = 0;
    private Plane plane = new Plane();
    private List<Modifier> modifiers = new ArrayList<>();
    private int selected;
    private int number;

    public static FigureConfiguration getSampleFigureConfiguration() {
        FigureConfiguration fc = new FigureConfiguration();
        fc.modifiers = new ArrayList<>();
        fc.modifiers.add(new OutlineModifier());
        fc.modifiers.add(new StrokedModifier(Color.red));
        fc.modifiers.add(new FilledModifier(Color.yellow));
        return fc;
    }

    @Override
    public String toString() {
        return String.format("Figure %d", number);
    }

    @Override
    public FigureConfiguration clone() {
        Plane newPlane = plane.clone();
        List<Modifier> newList = modifiers.stream().map(Modifier::clone).collect(Collectors.toList());
        return new FigureConfiguration(newPlane, newList, selected, number);
    }
}
