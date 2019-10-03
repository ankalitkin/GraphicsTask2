package ru.vsu.cs.course2;

import ru.vsu.cs.course2.graphics.*;

import javax.swing.*;
import java.awt.*;

public class EllipseDrawerForm {
    private JPanel rootPanel;
    private JPanel drawPanel;
    private JSlider startAngleSlider;
    private JSlider valueAngleSlider;
    private JRadioButton DDARadioButton;
    private JRadioButton bresenhamRadioButton;
    private JRadioButton filledRadioButton;
    private GraphicsProvider graphicsProvider;
    private PiePanel piePanel;
    private final Pie pie;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
        JFrame frame = new JFrame("Pie drawer by @kalitkin_a_v");
        frame.setContentPane(new EllipseDrawerForm().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    private EllipseDrawerForm() {
        graphicsProvider = new GraphicsProvider();
        graphicsProvider.setColor(Color.red);
        graphicsProvider.setPixelDrawer(new NativePixelDrawer(graphicsProvider));

        graphicsProvider.setPieDrawer(new PieDrawingWrapper(graphicsProvider));
        DDARadioButton.putClientProperty(LineDrawer.class, new DDALineDrawer(graphicsProvider));
        DDARadioButton.putClientProperty(EllipseDrawer.class, new DDAEllipseDrawer(graphicsProvider));
        bresenhamRadioButton.putClientProperty(LineDrawer.class, new BresenhamLineDrawer(graphicsProvider));
        bresenhamRadioButton.putClientProperty(EllipseDrawer.class, new BresenhamEllipseDrawer(graphicsProvider));
        filledRadioButton.putClientProperty(LineDrawer.class, new BresenhamLineDrawer(graphicsProvider));
        filledRadioButton.putClientProperty(EllipseDrawer.class, new BresenhamFilledEllipseDrawer(graphicsProvider));

        ButtonGroup bg = new ButtonGroup();
        bg.add(DDARadioButton);
        bg.add(bresenhamRadioButton);
        bg.add(filledRadioButton);
        DDARadioButton.setSelected(true);

        pie = new Pie();
        piePanel = new PiePanel(pie, graphicsProvider);
        drawPanel.setLayout(new GridLayout());
        drawPanel.add(piePanel);

        DDARadioButton.addActionListener((e) -> reInit());
        bresenhamRadioButton.addActionListener((e) -> reInit());
        filledRadioButton.addActionListener((e) -> reInit());

        startAngleSlider.addChangeListener(e -> {
            updatePie();
            repaint();
        });

        valueAngleSlider.addChangeListener(e -> {
            updatePie();
            repaint();
        });

        updatePie();
        reInit();
    }

    private void updatePie() {
        pie.startAngle = 2 * Math.PI * startAngleSlider.getValue() / startAngleSlider.getMaximum();
        pie.endAngle = 2 * Math.PI * valueAngleSlider.getValue() / valueAngleSlider.getMaximum() + pie.startAngle;
    }

    private void reInit() {
        JRadioButton selected = filledRadioButton.isSelected() ? filledRadioButton
                : (bresenhamRadioButton.isSelected() ? bresenhamRadioButton : DDARadioButton);

        graphicsProvider.setLineDrawer((LineDrawer) selected.getClientProperty(LineDrawer.class));
        graphicsProvider.setEllipseDrawer((EllipseDrawer) selected.getClientProperty(EllipseDrawer.class));
        repaint();
    }

    private void repaint() {
        piePanel.repaint();
    }

}
