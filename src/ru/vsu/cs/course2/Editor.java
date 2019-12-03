package ru.vsu.cs.course2;

import ru.vsu.cs.course2.Plane.Point;
import ru.vsu.cs.course2.figures.Stroke;
import ru.vsu.cs.course2.figures.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.List;

public class Editor extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {
    private static HashMap<RenderingHints.Key, Object> rh;
    private final Font numbersFont = new Font("Segoe UI", Font.PLAIN, 16);
    private final int dx = 4, dy = 4;
    private Plane plane;
    private Point selected;
    ScreenConverter sc;

    private java.awt.Point oldPoint = null;
    private int buttonNumber = -1;

    Editor(Plane plane) {
        this.plane = plane;
        rh = new HashMap<>();
        rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
    }

    public ScreenConverter getScreenConverter() {
        return sc;
    }

    public void setScreenConverter(ScreenConverter sc) {
        this.sc = sc;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    private void redraw() {
        paintImmediately(0, 0, getWidth(), getHeight());
    }

    @Override
    public void paintComponent(Graphics gg) {
        Graphics2D g = (Graphics2D) gg;
        g.setRenderingHints(rh);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        List<RealPoint> points = plane.getPoints();
        if (points.size() > 2) {
            Figure figure = new Figure(points, 0);
            Drawable drawable = new Filled(new Stroke(new ClosedCurved(figure), Color.red, 1), Color.yellow);
            //Drawable drawable = new Filled(new Stroke(figure, Color.red, 1), Color.yellow);
            drawable.draw(sc, g);
            //drawPointIcons(g, drawable.getOutlinePoints());
        }

        for (int i = 0; i < plane.points.size(); i++) {
            drawButton(g, i);
        }
    }

    private void drawButton(Graphics2D g, int i) {
        Point point = plane.points.get(i);
        g.setColor(Color.BLUE);
        g.fillRect(getX(point) - dx, getY(point) - dy, 2 * dx, 2 * dy);
        g.setFont(numbersFont);
        g.drawString(String.valueOf(i), getX(point) + dx, getY(point) + dy);
    }

    private void drawPointIcons(Graphics2D g, List<RealPoint> points) {
        int i = 0;
        for (RealPoint point : points) {
            ScreenPoint screenPoint = sc.realToScreen(point);
            int px = screenPoint.getX();
            int py = screenPoint.getY();
            g.setColor(Color.green);
            g.fillRect(px - dx, py - dy, 2 * dx, 2 * dy);
            g.setFont(numbersFont);
            g.drawString(String.valueOf(i++), px + dx, py + dy);
        }
    }

    private int getX(Point point) {
        return getScreenPoint(point).x;
    }

    private ScreenPoint getScreenPoint(Point point) {
        return sc.realToScreen(point.vect);
    }

    private int getY(Point point) {
        return getScreenPoint(point).y;
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        buttonNumber = e.getButton();
        selected = plane.getClosest(sc, e.getX(), e.getY(), dx, dy);
        if (selected == null && e.getButton() == MouseEvent.BUTTON1) {
            selected = plane.addNewPoint(sc, e.getX(), e.getY());
        } else if (selected != null && e.getButton() == MouseEvent.BUTTON3) {
            plane.points.remove(selected);
            selected = null;
        }
        redraw();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        buttonNumber = 0;
        oldPoint = null;
        selected = null;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (buttonNumber == MouseEvent.BUTTON2) {
            if (oldPoint != null) {
                int dx = e.getX() - oldPoint.x;
                int dy = e.getY() - oldPoint.y;
                sc.translateOnScreen(-dx, -dy);
            }
            oldPoint = e.getPoint();
            redraw();
        }

        if (selected == null)
            return;
        selected.vect = plane.VectorFromScreen(sc, e.getX(), e.getY());
        selected.z = plane.nextZ();
        redraw();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        sc.scale(e.getPoint(), e.getPreciseWheelRotation() * (-0.1));
        redraw();
    }
}