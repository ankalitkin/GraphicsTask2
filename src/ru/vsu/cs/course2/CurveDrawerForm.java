package ru.vsu.cs.course2;

import ru.vsu.cs.course2.figures.Curved;
import ru.vsu.cs.course2.figures.Drawable;
import ru.vsu.cs.course2.figures.Figure;
import ru.vsu.cs.course2.figures.Stroke;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CurveDrawerForm {
    private JPanel drawPanel;
    private JPanel rootPanel;
    private final ScreenConverter screenConverter;
    private final Canvas canvas;
    private JList figures;
    private JButton addButton;
    private JButton removeButton;
    private JCheckBox closedCheckBox;
    private JCheckBox curvedCheckBox;
    private JCheckBox strokeCheckBox;
    private JCheckBox filledCheckBox;
    private JSlider hStrokeSlider;
    private JSlider sStrokeSlider;
    private JSlider bStrokeSlider;
    private JSlider aStrokeSlider;
    private JSlider hFillSlider;
    private JSlider sFillSlider;
    private JSlider bFillSlider;
    private JSlider aFillSlider;
    private JButton saveButton;
    private JButton loadButton;
    private JSpinner spinner1;
    private Plane plane;
    private DefaultListModel<FigureConfiguration> figuresListModel = new DefaultListModel<>();
    private List<Drawable> figuresList;

    private CurveDrawerForm() {
        canvas = new Canvas();
        screenConverter = new ScreenConverter(canvas);
        plane = new Plane();
//        Drawable drawable = new Filled(new Stroke(new ClosedCurved(new Figure(plane)),
//                Color.red, 1), Color.yellow);
        Drawable drawable = new Stroke(new Curved(new Figure(plane)), Color.red, 1);
        figuresList.add(drawable);
        canvas.setFigures(figuresList);
        canvas.setEditor(editor);
        canvas.setScreenConverter(screenConverter);
        drawPanel.setLayout(new GridLayout());
        drawPanel.add(canvas);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
        JFrame frame = new JFrame("Curved drawer by @kalitkin_a_v");
        frame.setContentPane(new CurveDrawerForm().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 900);
        frame.setVisible(true);
    }

    private void updateFiguresList() {
        figuresList = Collections.list(figuresListModel.elements()).stream()
                .map(FigureBuilder::createFigure).collect(Collectors.toList());
    }

    private Color getStrokeColor() {
        int h = hStrokeSlider.getValue();
        int s = sStrokeSlider.getValue();
        int v = bStrokeSlider.getValue();
        int a = aStrokeSlider.getValue();
        int rgb = Color.HSBtoRGB(h / 255f, s / 255f, v / 255f);
        return new Color(a << 24 | rgb, true);
    }

    private Color getFillColor() {
        int h = hFillSlider.getValue();
        int s = sFillSlider.getValue();
        int v = bFillSlider.getValue();
        int a = aFillSlider.getValue();
        int rgb = Color.HSBtoRGB(h / 255f, s / 255f, v / 255f);
        return new Color(a << 24 | rgb, true);
    }

    private Editor createEditor() {
        Drawable value = figuresList.get(figures.getSelectedIndex());
        Plane plane = value.getPlane();
        return new Editor(plane, screenConverter, canvas::repaint);
    }

    private void onSelectedFigureChange() {
        updateFiguresList();
        canvas.setEditor(createEditor());
        canvas.repaint();
    }
}