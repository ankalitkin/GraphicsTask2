package ru.vsu.cs.course2;

import javax.swing.*;
import java.awt.*;

public class CurveDrawerForm {
    private JPanel drawPanel;
    private JPanel rootPanel;
    private Editor editor;
    private final Plane plane;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
        JFrame frame = new JFrame("Curved drawer by @kalitkin_a_v");
        frame.setContentPane(new CurveDrawerForm().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(512, 512);
        frame.setVisible(true);
    }

    private CurveDrawerForm() {
        plane = new Plane();
        editor = new Editor(plane);
        ScreenConverter screenConverter = new ScreenConverter(editor);
        editor.setScreenConverter(screenConverter);

        drawPanel.setLayout(new GridLayout());
        drawPanel.add(editor);
    }

}
