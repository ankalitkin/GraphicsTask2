package ru.vsu.cs.course2;

import ru.vsu.cs.course2.graphics.*;

import javax.swing.*;
import java.awt.*;

public class MainForm {
    private JPanel drawPanel;
    private JPanel rootPanel;
    private JRadioButton DDARadioButton;
    private JRadioButton bresenhamRadioButton;
    private JRadioButton wuRadioButton;
    private JCheckBox subpixelCheckBox;
    private JCheckBox upscaleX3CheckBox;
    private static Editor editor;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        JFrame frame = new JFrame("Task 8_15 by @kalitkin_a_v");
        frame.setContentPane(new MainForm().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(512, 512);
        frame.setVisible(true);
    }

    private MainForm() {
        ButtonGroup bg = new ButtonGroup();
        bg.add(DDARadioButton);
        bg.add(bresenhamRadioButton);
        bg.add(wuRadioButton);
        DDARadioButton.setSelected(true);


        Plane plane = new Plane();
        editor = new Editor(plane, graphics -> {
            PixelDrawer pixelDrawer = new NativePixelDrawer(graphics);

            LineDrawer lineDrawer;
            if (DDARadioButton.isSelected())
                lineDrawer = new DDALineDrawer(pixelDrawer);
            else if (bresenhamRadioButton.isSelected())
                lineDrawer = new BresenhamLineDrawer(pixelDrawer);
            else
                lineDrawer = new WuLineDrawer(pixelDrawer);

            return lineDrawer;
        });
        drawPanel.setLayout(new GridLayout());
        drawPanel.add(editor);

        DDARadioButton.addActionListener((e) -> repaint());
        bresenhamRadioButton.addActionListener((e) -> repaint());
        wuRadioButton.addActionListener((e) -> repaint());
    }

    private void repaint() {
        editor.invalidateCache();
        drawPanel.repaint();
    }
}
