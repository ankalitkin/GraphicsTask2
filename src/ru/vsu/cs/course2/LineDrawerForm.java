package ru.vsu.cs.course2;

import ru.vsu.cs.course2.graphics.*;

import javax.swing.*;
import java.awt.*;

public class LineDrawerForm {
    private JPanel drawPanel;
    private JPanel rootPanel;
    private JRadioButton DDARadioButton;
    private JRadioButton bresenhamRadioButton;
    private JRadioButton wuRadioButton;
    private Editor editor;
    private final GraphicsProvider graphicsProvider;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        JFrame frame = new JFrame("Line drawer by @kalitkin_a_v");
        frame.setContentPane(new LineDrawerForm().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(512, 512);
        frame.setVisible(true);
    }

    private LineDrawerForm() {
        graphicsProvider = new GraphicsProvider();
        graphicsProvider.setColor(Color.red);
        graphicsProvider.setPixelDrawer(new BufferedImagePixelDrawer(graphicsProvider));
        DDARadioButton.putClientProperty(LineDrawer.class, new DDALineDrawer(graphicsProvider));
        bresenhamRadioButton.putClientProperty(LineDrawer.class, new BresenhamLineDrawer(graphicsProvider));
        wuRadioButton.putClientProperty(LineDrawer.class, new WuLineDrawer(graphicsProvider));

        ButtonGroup bg = new ButtonGroup();
        bg.add(DDARadioButton);
        bg.add(bresenhamRadioButton);
        bg.add(wuRadioButton);
        DDARadioButton.setSelected(true);

        Plane plane = new Plane();
        editor = new Editor(plane, graphicsProvider);
        drawPanel.setLayout(new GridLayout());
        drawPanel.add(editor);

        DDARadioButton.addActionListener((e) -> reInit());
        bresenhamRadioButton.addActionListener((e) -> reInit());
        wuRadioButton.addActionListener((e) -> reInit());
        reInit();
    }

    private void reInit() {
        if (DDARadioButton.isSelected())
            graphicsProvider.setLineDrawer((LineDrawer) DDARadioButton.getClientProperty(LineDrawer.class));
        else if (bresenhamRadioButton.isSelected())
            graphicsProvider.setLineDrawer((LineDrawer) bresenhamRadioButton.getClientProperty(LineDrawer.class));
        else
            graphicsProvider.setLineDrawer((LineDrawer) wuRadioButton.getClientProperty(LineDrawer.class));
        repaint();
    }

    private void repaint() {
        editor.invalidateCache();
        editor.repaint();
    }
}
