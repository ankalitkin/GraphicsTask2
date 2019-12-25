package ru.vsu.cs.course2.modifiers;

import ru.vsu.cs.course2.figures.Drawable;

import javax.swing.*;

public interface Modifier {
    Drawable build(Drawable drawable);

    JPanel getConfigPanel();

    void setOnChangedCallback(Runnable runnable);

    Modifier clone();
}
