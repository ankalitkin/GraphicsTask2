package ru.vsu.cs.course2;

import ru.vsu.cs.course2.graphics.CurveDrawer;
import ru.vsu.cs.course2.graphics.GraphicsProvider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import static ru.vsu.cs.course2.CurveDrawerForm.FPS;

public class CurvePlayer extends JPanel implements ActionListener {
    private GraphicsProvider graphicsProvider;
    private CurveProcessor curveProcessor;
    private int duration;
    private int frame = 0;
    private Timer timer = new Timer(1000 / FPS, this);

    public CurvePlayer(GraphicsProvider graphicsProvider, CurveProcessor curveProcessor, int duration) {
        this.graphicsProvider = graphicsProvider;
        this.curveProcessor = curveProcessor;
        this.duration = duration;
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics gg) {
        Graphics2D g = (Graphics2D) gg;
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        graphicsProvider.setBufferedImage(bi);
        CurveDrawer cd = graphicsProvider.getCurveDrawer();
        cd.drawCurve(curveProcessor.lerp(((double) frame) / duration));

        g.drawImage(bi, 0, 0, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        paintImmediately(0, 0, getWidth(), getHeight());
        if (frame++ > duration) {
            stop();
        }
    }

    public void stop() {
        if (timer != null)
            timer.stop();
        timer = null;
    }
}
