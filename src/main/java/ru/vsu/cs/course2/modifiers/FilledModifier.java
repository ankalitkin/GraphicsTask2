package ru.vsu.cs.course2.modifiers;

import ru.vsu.cs.course2.figures.Drawable;
import ru.vsu.cs.course2.figures.Filled;

import javax.swing.*;
import java.awt.*;

public class FilledModifier implements Modifier {
    private JPanel configPanel;
    private JSlider hSlider = new JSlider(0, 360);
    private JSlider sSlider = new JSlider(0, 255);
    private JSlider vSlider = new JSlider(0, 255);
    private JSlider aSlider = new JSlider(0, 255);
    private Runnable onChangedCallback;

    public FilledModifier(Color color) {
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
        if (color != null)
            setColor(color);
        hSlider.addChangeListener(e -> onChanged());
        sSlider.addChangeListener(e -> onChanged());
        vSlider.addChangeListener(e -> onChanged());
        aSlider.addChangeListener(e -> onChanged());
    }

    public FilledModifier() {
        this(null);
    }

    private void onChanged() {
        if (onChangedCallback == null)
            return;
        onChangedCallback.run();
    }

    @Override
    public Drawable build(Drawable drawable) {
        return new Filled(drawable, getColor());
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
        return new FilledModifier(getColor());
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
