package ru.vsu.cs.course2;

import ru.vsu.cs.course2.figures.Drawable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.util.List;

public class Canvas extends JPanel {
    private List<Drawable> figures;
    private ScreenConverter screenConverter;
    private Editor editor;

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        if (figures != null) {
            for (Drawable figure : figures) {
                figure.draw(screenConverter, g2d);
            }
        }
        if (editor != null) {
            editor.draw(screenConverter, g2d);
        }
    }

    public List<Drawable> getFigures() {
        return figures;
    }

    public void setFigures(List<Drawable> figures) {
        this.figures = figures;
    }

    public ScreenConverter getScreenConverter() {
        return screenConverter;
    }

    public void setScreenConverter(ScreenConverter screenConverter) {
        this.screenConverter = screenConverter;
    }

    public Editor getEditor() {
        return editor;
    }

    public void setEditor(Editor editor) {
        this.editor = editor;
        resetListeners();
        this.addMouseListener(editor);
        this.addMouseMotionListener(editor);
        this.addMouseWheelListener(editor);
    }

    private void resetListeners() {
        for (MouseListener listener : getMouseListeners()) {
            removeMouseListener(listener);
        }
        for (MouseMotionListener listener : getMouseMotionListeners()) {
            removeMouseMotionListener(listener);
        }
        for (MouseWheelListener listener : getMouseWheelListeners()) {
            removeMouseWheelListener(listener);
        }
    }
}