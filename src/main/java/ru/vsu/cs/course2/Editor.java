package ru.vsu.cs.course2;

import ru.vsu.cs.course2.figures.Drawable;
import ru.vsu.cs.course2.figures.RealPoint;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.List;

public class Editor extends Drawable implements MouseListener, MouseMotionListener, MouseWheelListener {
    private static HashMap<RenderingHints.Key, Object> rh;

    static {
        rh = new HashMap<>();
        rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    }

    private final Font numbersFont = new Font("Segoe UI", Font.PLAIN, 16);
    private final int dx = 4, dy = 4;
    private Plane plane;
    private Point selected;
    private java.awt.Point oldPoint = null;
    private int buttonNumber = -1;

    private ScreenConverter sc;
    private Runnable redrawCallback;

    public Editor(Plane plane, ScreenConverter sc, Runnable redrawCallback) {
        this.plane = plane;
        this.sc = sc;
        this.redrawCallback = redrawCallback;
    }

    public Editor(Plane plane, Runnable redrawCallback) {
        this.plane = plane;
        this.redrawCallback = redrawCallback;
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

    @Override
    public void draw(ScreenConverter screenConverter, Graphics2D g) {
        g.setRenderingHints(rh);
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
        redrawCallback.run();
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
            redrawCallback.run();
        }

        if (selected == null)
            return;
        selected.vect = plane.VectorFromScreen(sc, e.getX(), e.getY());
        selected.z = plane.nextZ();
        redrawCallback.run();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        sc.scale(e.getPoint(), e.getPreciseWheelRotation() * (-0.1));
        redrawCallback.run();
    }

    @Override
    public List<RealPoint> getOutlinePoints() {
        return null;
    }

}