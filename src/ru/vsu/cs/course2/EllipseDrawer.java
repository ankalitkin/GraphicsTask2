package ru.vsu.cs.course2;

import ru.vsu.cs.course2.graphics.*;

import javax.swing.*;
import java.awt.*;

public class EllipseDrawer {
    private JPanel rootPanel;
    private JPanel drawPanel;
    private JSlider startAngleSlider;
    private JSlider valueAngleSlider;
    private JCheckBox filledCheckBox;
    private JRadioButton DDARadioButton;
    private JRadioButton bresenhamRadioButton;
    private JRadioButton wuRadioButton;
    private GraphicsProvider graphicsProvider;
    private PiePanel piePanel;
    private final Pie pie;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
        JFrame frame = new JFrame("Pie & ellipse drawer by @kalitkin_a_v");
        frame.setContentPane(new EllipseDrawer().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    private EllipseDrawer() {
        graphicsProvider = new GraphicsProvider();
        graphicsProvider.setColor(Color.red);
        graphicsProvider.setPixelDrawer(new NativePixelDrawer(graphicsProvider));

        graphicsProvider.setEllipseDrawer(new DDAEllipseDrawer(graphicsProvider));
        //Todo: add Bresenhaim and Wu ellipse drawers

        graphicsProvider.setPieDrawer(new PieDrawingWrapper(graphicsProvider));
        DDARadioButton.putClientProperty(LineDrawer.class, new DDALineDrawer(graphicsProvider));
        bresenhamRadioButton.putClientProperty(LineDrawer.class, new BresenhamLineDrawer(graphicsProvider));
        wuRadioButton.putClientProperty(LineDrawer.class, new WuLineDrawer(graphicsProvider));

        ButtonGroup bg = new ButtonGroup();
        bg.add(DDARadioButton);
        bg.add(bresenhamRadioButton);
        bg.add(wuRadioButton);
        DDARadioButton.setSelected(true);

        pie = new Pie();
        piePanel = new PiePanel(pie, graphicsProvider);
        drawPanel.setLayout(new GridLayout());
        drawPanel.add(piePanel);

        DDARadioButton.addActionListener((e) -> reInit());
        bresenhamRadioButton.addActionListener((e) -> reInit());
        wuRadioButton.addActionListener((e) -> reInit());

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
        if (DDARadioButton.isSelected())
            graphicsProvider.setLineDrawer((LineDrawer) DDARadioButton.getClientProperty(LineDrawer.class));
        else if (bresenhamRadioButton.isSelected())
            graphicsProvider.setLineDrawer((LineDrawer) bresenhamRadioButton.getClientProperty(LineDrawer.class));
        else
            graphicsProvider.setLineDrawer((LineDrawer) wuRadioButton.getClientProperty(LineDrawer.class));
        repaint();
    }

    private void repaint() {
        piePanel.repaint();
    }

}
