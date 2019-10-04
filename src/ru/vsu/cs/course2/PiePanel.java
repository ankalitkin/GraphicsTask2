package ru.vsu.cs.course2;

import ru.vsu.cs.course2.graphics.GraphicsProvider;
import ru.vsu.cs.course2.graphics.PieDrawer;

import javax.swing.*;
import java.awt.*;

public class PiePanel extends JPanel {
    private Pie pie;
    private GraphicsProvider graphicsProvider;

    public PiePanel(Pie pie, GraphicsProvider graphicsProvider) {
        this.pie = pie;
        this.graphicsProvider = graphicsProvider;
    }

    @Override
    protected void paintComponent(Graphics gr) {
        Graphics2D g = (Graphics2D) gr;
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());
        PieDrawer pieDrawer = graphicsProvider.getPieDrawer();
        int w6 = getWidth() / 6;
        int h6 = getHeight() / 6;
        pieDrawer.drawPie(g, w6, h6, 5 * w6, 5 * h6, pie.startAngle, pie.endAngle);
    }
}
