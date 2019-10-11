package ru.vsu.cs.course2;

import ru.vsu.cs.course2.graphics.BufferedImagePixelDrawer;
import ru.vsu.cs.course2.graphics.GraphicsProvider;
import ru.vsu.cs.course2.graphics.PieDrawer;
import ru.vsu.cs.course2.graphics.PixelDrawer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class PiePanel extends JPanel {
    private Pie pie;
    private GraphicsProvider graphicsProvider;

    public PiePanel(Pie pie, GraphicsProvider graphicsProvider) {
        this.pie = pie;
        this.graphicsProvider = graphicsProvider;
    }

    @Override
    protected void paintComponent(Graphics gr) {
        PixelDrawer pd = graphicsProvider.getPixelDrawer();
        BufferedImage image = null;
        if (pd instanceof BufferedImagePixelDrawer) {
            image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
            graphicsProvider.setBufferedImage(image);
            ((BufferedImagePixelDrawer) pd).fillColor(Color.white);
        } else {
            Graphics2D g = (Graphics2D) gr;
            graphicsProvider.setGraphics(g);
            g.setColor(Color.white);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        PieDrawer pieDrawer = graphicsProvider.getPieDrawer();
        int w6 = getWidth() / 6;
        int h6 = getHeight() / 6;
        pieDrawer.drawPie(w6, h6, 5 * w6, 5 * h6, pie.startAngle, pie.endAngle);
        if (image != null) {
            gr.drawImage(image, 0, 0, null);
        }
    }
}
