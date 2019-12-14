package ru.vsu.cs.course2;

import ru.vsu.cs.course2.figures.Drawable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class CurveDrawerForm {
    private static final String CHOOSE_OPEN_FILE_MESSAGE = "Open file";
    private static final String CHOOSE_SAVE_FILE_MESSAGE = "Save file";
    private final ScreenConverter screenConverter;
    private final Canvas canvas;
    private JPanel drawPanel;
    private JPanel rootPanel;
    private JList<FigureConfiguration> figures;
    private JButton addButton;
    private JButton removeButton;
    private JCheckBox closedCheckBox;
    private JCheckBox curvedCheckBox;
    private JCheckBox strokedCheckBox;
    private JCheckBox filledCheckBox;
    private JSlider hStrokeSlider;
    private JSlider sStrokeSlider;
    private JSlider vStrokeSlider;
    private JSlider aStrokeSlider;
    private JSlider hFillSlider;
    private JSlider sFillSlider;
    private JSlider vFillSlider;
    private JSlider aFillSlider;
    private JButton saveButton;
    private JButton loadButton;
    private JSpinner strokeThicknessSlider;
    private JCheckBox visibleCheckBox;
    private JButton upButton;
    private JButton downButton;
    private DefaultListModel<FigureConfiguration> figuresListModel = new DefaultListModel<>();
    private List<Drawable> figuresList = new LinkedList<>();
    private boolean updating;

    private CurveDrawerForm() {
        canvas = new Canvas();
        canvas.setReversed(true);
        screenConverter = new ScreenConverter(canvas);
        canvas.setEditor(createEditor());
        canvas.setScreenConverter(screenConverter);
        drawPanel.setLayout(new GridLayout());
        drawPanel.add(canvas);
        figures.setModel(figuresListModel);
        figures.addListSelectionListener(e -> onSelectedFigureChange());
        figuresListModel.add(0, FigureConfiguration.getSampleFigureConfiguration());
        figures.setSelectedIndex(0);

        strokeThicknessSlider.setModel(new SpinnerNumberModel(1, 0, 10, 1));

        addButton.addActionListener(e -> {
            figuresListModel.add(0, new FigureConfiguration());
            figures.setSelectedIndex(0);
            updateFigureConfiguration();
        });

        removeButton.addActionListener(e -> {
            int index = figures.getSelectedIndex();
            if (index < 0)
                return;
            figuresListModel.remove(index);
            figures.repaint();
        });

        upButton.addActionListener(e -> {
            int index = figures.getSelectedIndex();
            if (index < 1)
                return;
            FigureConfiguration fc = figuresListModel.get(index);
            figuresListModel.remove(index);
            figuresListModel.add(index - 1, fc);
            figures.setSelectedIndex(index - 1);
        });

        downButton.addActionListener(e -> {
            int index = figures.getSelectedIndex();
            if (index < 0 || index > figuresListModel.size() - 2)
                return;
            FigureConfiguration fc = figuresListModel.get(index);
            figuresListModel.remove(index);
            figuresListModel.add(index + 1, fc);
            figures.setSelectedIndex(index + 1);
        });

        visibleCheckBox.addActionListener(e -> updateFigureConfiguration());
        closedCheckBox.addActionListener(e -> updateFigureConfiguration());
        curvedCheckBox.addActionListener(e -> updateFigureConfiguration());
        strokedCheckBox.addActionListener(e -> updateFigureConfiguration());
        filledCheckBox.addActionListener(e -> updateFigureConfiguration());
        strokeThicknessSlider.addChangeListener(e -> updateFigureConfiguration());
        hStrokeSlider.addChangeListener(e -> updateFigureConfiguration());
        sStrokeSlider.addChangeListener(e -> updateFigureConfiguration());
        vStrokeSlider.addChangeListener(e -> updateFigureConfiguration());
        aStrokeSlider.addChangeListener(e -> updateFigureConfiguration());
        hFillSlider.addChangeListener(e -> updateFigureConfiguration());
        sFillSlider.addChangeListener(e -> updateFigureConfiguration());
        vFillSlider.addChangeListener(e -> updateFigureConfiguration());
        aFillSlider.addChangeListener(e -> updateFigureConfiguration());
        saveButton.addActionListener(e -> {
            Picture picture = getPicture();
            String filename = browseSaveFile();
            if (filename == null)
                return;
            Utils.saveToFile(filename, picture);
        });

        loadButton.addActionListener(e -> {
            String filename = browseOpenFile();
            if (filename == null)
                return;
            Picture picture = Utils.loadFromFile(filename);
            if (picture == null)
                return;
            setPicture(picture);
        });
    }

    private Picture getPicture() {
        List<FigureConfiguration> list = new ArrayList<>();
        for (int i = 0; i < figuresListModel.size(); i++) {
            list.add(figuresListModel.get(i));
        }
        return new Picture(list);
    }

    private void setPicture(Picture picture) {
        figuresListModel.clear();
        if (picture == null)
            return;
        List<FigureConfiguration> list = picture.getFigures();
        for (FigureConfiguration fc : list) {
            figuresListModel.addElement(fc);
        }
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

    private List<Drawable> getFiguresList() {
        return Collections.list(figuresListModel.elements()).stream()
                .map(FigureBuilder::createFigure).collect(Collectors.toList());
    }

    private Color getStrokeColor() {
        int h = hStrokeSlider.getValue();
        int s = sStrokeSlider.getValue();
        int v = vStrokeSlider.getValue();
        int a = aStrokeSlider.getValue();
        float hm = hStrokeSlider.getMaximum();
        float sm = sStrokeSlider.getMaximum();
        float vm = vStrokeSlider.getMaximum();
        float am = aStrokeSlider.getMaximum();
        float[] rgba = new float[4];
        Color.getHSBColor(h / hm, s / sm, v / vm).getRGBComponents(rgba);
        return new Color(rgba[0], rgba[1], rgba[2], a / am);
    }

    private void setStrokeColor(Color color) {
        float[] hsva = new float[4];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsva);
        float hm = hStrokeSlider.getMaximum();
        float sm = sStrokeSlider.getMaximum();
        float vm = vStrokeSlider.getMaximum();
        float am = aStrokeSlider.getMaximum();
        hStrokeSlider.setValue((int) (hsva[0] * hm));
        sStrokeSlider.setValue((int) (hsva[1] * sm));
        vStrokeSlider.setValue((int) (hsva[2] * vm));
        aStrokeSlider.setValue((int) (color.getAlpha() / 255f * am));
    }

    private Color getFillColor() {
        int h = hFillSlider.getValue();
        int s = sFillSlider.getValue();
        int v = vFillSlider.getValue();
        int a = aFillSlider.getValue();
        float hm = hFillSlider.getMaximum();
        float sm = sFillSlider.getMaximum();
        float vm = vFillSlider.getMaximum();
        float am = aFillSlider.getMaximum();
        float[] rgba = new float[4];
        Color.getHSBColor(h / hm, s / sm, v / vm).getRGBComponents(rgba);
        return new Color(rgba[0], rgba[1], rgba[2], a / am);
    }

    private void setFillColor(Color color) {
        float[] hsva = new float[4];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsva);
        float hm = hFillSlider.getMaximum();
        float sm = sFillSlider.getMaximum();
        float vm = vFillSlider.getMaximum();
        float am = aFillSlider.getMaximum();
        hFillSlider.setValue((int) (hsva[0] * hm));
        sFillSlider.setValue((int) (hsva[1] * sm));
        vFillSlider.setValue((int) (hsva[2] * vm));
        aFillSlider.setValue((int) (color.getAlpha() / 255f * am));
    }


    private FigureConfiguration getFigureConfiguration() {
        int index = figures.getSelectedIndex();
        if (index < 0)
            return null;
        return figuresListModel.get(index);
    }

    private void updateFigureConfiguration() {
        if (updating)
            return;
        FigureConfiguration fc = getFigureConfiguration();
        if (fc == null)
            return;
        fc.setVisible(visibleCheckBox.isSelected());
        fc.setClosed(closedCheckBox.isSelected());
        fc.setCurved(curvedCheckBox.isSelected());
        fc.setStroked(strokedCheckBox.isSelected());
        fc.setFilled(filledCheckBox.isSelected());
        fc.setStrokeColor(getStrokeColor());
        fc.setStrokeThickness(getStrokeThickness());
        fc.setFillColor(getFillColor());
        figuresList.set(figures.getSelectedIndex(), FigureBuilder.createFigure(fc));
        drawPanel.repaint();
        figures.repaint();
    }

    private void loadFC() {
        updating = true;
        FigureConfiguration fc = getFigureConfiguration();
        if (fc == null)
            return;

        visibleCheckBox.setSelected(fc.isVisible());
        closedCheckBox.setSelected(fc.isClosed());
        curvedCheckBox.setSelected(fc.isCurved());
        strokedCheckBox.setSelected(fc.isStroked());
        filledCheckBox.setSelected(fc.isFilled());
        strokeThicknessSlider.setValue(fc.getStrokeThickness());
        setStrokeColor(fc.getStrokeColor());
        setFillColor(fc.getFillColor());
        updating = false;
    }

    private int getStrokeThickness() {
        return (int) strokeThicknessSlider.getValue();
    }

    private Editor createEditor() {
        FigureConfiguration fc = getFigureConfiguration();
        if (fc == null)
            return null;
        return new Editor(fc.getPlane(), screenConverter, canvas::repaint);
    }

    private void onSelectedFigureChange() {
        loadFC();
        figuresList = getFiguresList();
        canvas.setFigures(figuresList);
        canvas.setEditor(createEditor());
        canvas.repaint();
    }

    private String browseSaveFile() {
        FileDialog fileDialog = new FileDialog((Frame) null, CHOOSE_SAVE_FILE_MESSAGE);
        fileDialog.setMode(FileDialog.SAVE);
        fileDialog.setFile("figure.json");
        fileDialog.setVisible(true);

        String dir = fileDialog.getDirectory();
        String file = fileDialog.getFile();
        if (dir != null && file != null) {
            if (file.contains("."))
                return dir + file;
            else
                return String.format("%s%s.json", dir, file);
        }
        return null;
    }

    private String browseOpenFile() {
        FileDialog fileDialog = new FileDialog((Frame) null, CHOOSE_OPEN_FILE_MESSAGE);
        fileDialog.setMode(FileDialog.LOAD);
        fileDialog.setVisible(true);

        String dir = fileDialog.getDirectory();
        String file = fileDialog.getFile();
        if (dir != null && file != null) {
            return dir + file;
        }
        return null;
    }
}