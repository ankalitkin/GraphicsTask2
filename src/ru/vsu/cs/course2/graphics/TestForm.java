package ru.vsu.cs.course2.graphics;

import ru.vsu.cs.course2.Pie;
import ru.vsu.cs.course2.PiePanel;

import javax.swing.*;
import java.awt.*;

public class TestForm {
    private JPanel root;

    public static void main(String[] args) {
        JFrame frame = new JFrame("TestForm");
        TestForm testForm = new TestForm();
        frame.setContentPane(testForm.root);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
        frame.setVisible(true);

        JPanel panel = testForm.root;
        GridLayout mgr = new GridLayout();
        panel.setLayout(mgr);
        int rows = 8;
        int cols = 8;
        mgr.setRows(rows + 1);
        mgr.setColumns(cols);

        GraphicsProvider graphicsProvider = new GraphicsProvider();
        graphicsProvider.setColor(Color.red);
        graphicsProvider.setPixelDrawer(new BufferedImagePixelDrawer(graphicsProvider));
        graphicsProvider.setPieDrawer(new PieDrawingWrapper(graphicsProvider));
        graphicsProvider.setLineDrawer(new BresenhamLineDrawer(graphicsProvider));
        graphicsProvider.setEllipseDrawer(new BresenhamFilledEllipseDrawer(graphicsProvider));

        for (int i = 0; i <= rows; i++) {
            for (int j = 1; j <= cols; j++) {
                Pie pie = new Pie();
                PiePanel piePanel = new PiePanel(pie, graphicsProvider);
                pie.startAngle = 2 * Math.PI / rows * i;
                double angle = 2 * Math.PI / rows * j;
                pie.endAngle = pie.startAngle + angle;
                panel.add(piePanel);
            }
        }
        panel.revalidate();
        panel.repaint();
    }
}