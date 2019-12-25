package ru.vsu.cs.course2.modifiers;

import ru.vsu.cs.course2.figures.Closed;
import ru.vsu.cs.course2.figures.ClosedCurved;
import ru.vsu.cs.course2.figures.Curved;
import ru.vsu.cs.course2.figures.Drawable;

import javax.swing.*;
import java.awt.*;

public class OutlineModifier implements Modifier {
    private JPanel configPanel;
    private Runnable onChangedCallback;
    private JCheckBox visibleCheckBox;
    private JCheckBox closedCheckBox;
    private JCheckBox curvedCheckBox;

    public OutlineModifier(boolean visible, boolean closed, boolean curved) {
        configPanel = new JPanel() {
        };
        configPanel.setLayout(new GridLayout());
        visibleCheckBox = new JCheckBox("Visible");
        visibleCheckBox.setSelected(visible);
        closedCheckBox = new JCheckBox("Closed");
        closedCheckBox.setSelected(closed);
        curvedCheckBox = new JCheckBox("Curved");
        curvedCheckBox.setSelected(curved);
        configPanel.add(visibleCheckBox);
        configPanel.add(closedCheckBox);
        configPanel.add(curvedCheckBox);
        visibleCheckBox.addActionListener(e -> onChanged());
        closedCheckBox.addActionListener(e -> onChanged());
        curvedCheckBox.addActionListener(e -> onChanged());
    }

    public OutlineModifier() {
        this(true, true, false);
    }

    private void onChanged() {
        if (onChangedCallback == null)
            return;
        onChangedCallback.run();
    }

    @Override
    public Drawable build(Drawable drawable) {
        if (!isVisible())
            return drawable;
        if (isCurved()) {
            if (isClosed())
                drawable = new ClosedCurved(drawable);
            else
                drawable = new Curved(drawable);
        } else {
            if (isClosed())
                drawable = new Closed(drawable);
        }
        return drawable;
    }

    @Override
    public JPanel getConfigPanel() {
        return configPanel;
    }

    @Override
    public void setOnChangedCallback(Runnable runnable) {
        onChangedCallback = runnable;
    }

    private boolean isVisible() {
        return visibleCheckBox.isSelected();
    }

    private boolean isClosed() {
        return closedCheckBox.isSelected();
    }

    private boolean isCurved() {
        return curvedCheckBox.isSelected();
    }

    @Override
    public String toString() {
        return "OutlineModifier";
    }

    @Override
    public OutlineModifier clone() {
        return new OutlineModifier(isVisible(), isClosed(), isCurved());
    }
}
