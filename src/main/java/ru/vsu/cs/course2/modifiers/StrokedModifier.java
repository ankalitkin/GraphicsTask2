package ru.vsu.cs.course2.modifiers;

import ru.vsu.cs.course2.figures.Drawable;
import ru.vsu.cs.course2.figures.Stroked;

import javax.swing.*;
import java.awt.*;

public class StrokedModifier implements Modifier {
    private JPanel configPanel;
    private JSlider hSlider = new JSlider(0, 360);
    private JSlider sSlider = new JSlider(0, 255);
    private JSlider vSlider = new JSlider(0, 255);
    private JSlider aSlider = new JSlider(0, 255);
    private JSpinner thicknessSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 100, 1));
    private Runnable onChangedCallback;

    public StrokedModifier(Color color, Double thickness) {
        configPanel = new JPanel();
        GridLayout mgr = new GridLayout();
        mgr.setColumns(2);
        configPanel.setLayout(mgr);
        configPanel.add(new Label("H: "));
        configPanel.add(hSlider);
        configPanel.add(new Label("S: "));
        configPanel.add(sSlider);
        configPanel.add(new Label("V: "));
        configPanel.add(vSlider);
        configPanel.add(new Label("A: "));
        configPanel.add(aSlider);
        configPanel.add(new Label("Thickness: "));
        configPanel.add(thicknessSpinner);
        if (color != null)
            setColor(color);
        if (thickness != null)
            thicknessSpinner.setValue(thickness);
        hSlider.addChangeListener(e -> onChanged());
        sSlider.addChangeListener(e -> onChanged());
        vSlider.addChangeListener(e -> onChanged());
        aSlider.addChangeListener(e -> onChanged());
    }

    public StrokedModifier(Color color, double thickness) {
        this(color, (Double) thickness);
    }

    public StrokedModifier(Color color) {
        this(color, null);
    }

    public StrokedModifier() {
        this(null, null);
    }

    private void onChanged() {
        if (onChangedCallback == null)
            return;
        onChangedCallback.run();
    }

    @Override
    public Drawable build(Drawable drawable) {
        return new Stroked(drawable, getColor(), getThickness());
    }

    private double getThickness() {
        return (double) (int) thicknessSpinner.getValue();
    }

    @Override
    public JPanel getConfigPanel() {
        return configPanel;
    }

    @Override
    public void setOnChangedCallback(Runnable runnable) {
        onChangedCallback = runnable;
    }

    @Override
    public Modifier clone() {
        return new StrokedModifier(getColor(), getThickness());
    }

    private Color getColor() {
        int h = hSlider.getValue();
        int s = sSlider.getValue();
        int v = vSlider.getValue();
        int a = aSlider.getValue();
        float hm = hSlider.getMaximum();
        float sm = sSlider.getMaximum();
        float vm = vSlider.getMaximum();
        float am = aSlider.getMaximum();
        float[] rgba = new float[4];
        Color.getHSBColor(h / hm, s / sm, v / vm).getRGBComponents(rgba);
        return new Color(rgba[0], rgba[1], rgba[2], a / am);
    }

    private void setColor(Color color) {
        float[] hsva = new float[4];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsva);
        float hm = hSlider.getMaximum();
        float sm = sSlider.getMaximum();
        float vm = vSlider.getMaximum();
        float am = aSlider.getMaximum();
        hSlider.setValue((int) (hsva[0] * hm));
        sSlider.setValue((int) (hsva[1] * sm));
        vSlider.setValue((int) (hsva[2] * vm));
        aSlider.setValue((int) (color.getAlpha() / 255f * am));
    }
}
