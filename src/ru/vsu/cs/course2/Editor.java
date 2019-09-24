package ru.vsu.cs.course2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.function.Function;

import ru.vsu.cs.course2.Plane.Point;
import ru.vsu.cs.course2.graphics.LineDrawer;

public class Editor extends JPanel {
    private static HashMap<RenderingHints.Key, Object> rh;
    private final Font numbersFont = new Font("Segoe UI", Font.PLAIN, 16);
    private final Font fontLabel = new Font("Segoe UI", Font.BOLD, 32);
    private final int dx = 4, dy = 4;
    private JComponent parent;
    private JLabel label;
    private Plane plane;
    private Point selected;
    private Function<Graphics2D, LineDrawer> getDrawer;
    //cache
    private BufferedImage imageCache;

    Editor(Plane plane, Function<Graphics2D, LineDrawer> getDrawer) {
        this.plane = plane;
        this.getDrawer = getDrawer;
        rh = new HashMap<>();
        rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                onMousePressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                onMouseReleased();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                onMouseDragged(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
            }
        });
    }

    /*private void repaint() {
        label.paintImmediately(0, 0, getIconWidth(), getIconHeight());
    }*/

    private void onMousePressed(MouseEvent e) {
        selected = plane.getClosest(e.getX(), e.getY(), dx, dy);
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (selected == null) {
                selected = plane.addNewPoint(e.getX(), e.getY());
            } else if (e.isControlDown()) {
                selected = plane.insertBefore(e.getX(), e.getY(), selected);
                imageCache = null;
            } else if (e.isShiftDown()) {
                selected = plane.insertAfter(e.getX(), e.getY(), selected);
                imageCache = null;
            } else {
                imageCache = null;
            }
        } else if (selected != null && e.getButton() == MouseEvent.BUTTON3) {
            plane.points.remove(selected);
            selected = null;
            imageCache = null;
        }
        repaint();
    }

    private void onMouseReleased() {
        selected = null;
        imageCache = null;
        revalidateCache();
    }

    private void onMouseDragged(MouseEvent e) {
        if (selected == null)
            return;
        selected.x = e.getX();
        selected.y = e.getY();
        selected.z = plane.nextZ();
        repaint();
    }

    @Override
    public void paintComponent(Graphics gg) {
        if(!isCacheValid()) {
            revalidateCache();
        }
        Graphics2D g = (Graphics2D) gg;
        g.setRenderingHints(rh);
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());

        for (int i = 0; i < plane.points.size(); i++) {
            Point point = plane.points.get(i);
            g.setColor(Color.BLUE);
            g.fillRect(point.x - dx, point.y - dy, 2 * dx, 2 * dy);
            g.setFont(numbersFont);
            g.drawString(String.valueOf(i), point.x + dx, point.y + dy);
        }

        gg.drawImage(imageCache, 0, 0, null);

        LineDrawer lineDrawer = getDrawer.apply(g);
        int size = plane.points.size();
        for (int i = 0; i < size - 1; i++) {
            Point p1 = plane.points.get(i);
            Point p2 = plane.points.get((i + 1) % size);
            //g.setColor(Color.RED);
            //g.drawLine(p1.x, p1.y, p2.x, p2.y);
            if (p1.equals(selected) || p2.equals(selected))
                lineDrawer.drawLine(p1.x, p1.y, p2.x, p2.y, Color.red);
        }

        //g.setFont(fontLabel);
        //g.setColor(Color.BLUE);
        //int width = g.getFontMetrics().stringWidth(perimeter);
        //g.drawString(perimeter, getIconWidth() - width - 10, getIconHeight() - 10);
    }

    private void revalidateCache() {
        imageCache = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = imageCache.createGraphics();

        g.setColor(new Color(255, 255, 255, 0));
        g.fillRect(0, 0, getWidth(), getHeight());

        LineDrawer lineDrawer = getDrawer.apply(g);

        //Draw all except selected
        int size = plane.points.size();
        for (int i = 0; i < size - 1; i++) {
            Point p1 = plane.points.get(i);
            Point p2 = plane.points.get((i + 1) % size);
            //g.setColor(Color.RED);
            //g.drawLine(p1.x, p1.y, p2.x, p2.y);
            if (!p1.equals(selected) && !p2.equals(selected))
                lineDrawer.drawLine(p1.x, p1.y, p2.x, p2.y, Color.red);
        }
        g.dispose();
    }

    private boolean isCacheValid() {
        return imageCache != null &&
                imageCache.getWidth() == getWidth() &&
                imageCache.getHeight() == getHeight();
    }

    public void invalidateCache() {
        imageCache = null;
    }

}
