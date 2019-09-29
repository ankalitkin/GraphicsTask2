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
        pieDrawer.drawPie(g, 100, 100, 500, 400, pie.startAngle, pie.endAngle);
    }
}
