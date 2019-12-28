package ru.vsu.cs.course2;

import ru.vsu.cs.course2.figures.Drawable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Canvas extends JPanel {
    private List<Drawable> figures;
    private ScreenConverter screenConverter;
    private Editor editor;
    private boolean reversed;
    private Runnable operationCallback;

    @Override
    protected void paintComponent(Graphics g) {
        BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        if (figures == null)
            return;
        List<Drawable> drawList = new ArrayList<>(figures);
        if (reversed)
            Collections.reverse(drawList);

        int counter = 0;
        List<Thread> threads = new ArrayList<>();
        StringBuffer sb = new StringBuffer();
        for (Drawable figure : drawList) {
            int num = counter++;
            Graphics2D grr = img.createGraphics();
            grr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Drawable lastCrossing = getLastCrossing(drawList, figure);
            if (lastCrossing == null) {
                threads.add(new Thread(() -> drawIndependently(grr, figure, num, sb)));
            } else {
                int otherNum = drawList.indexOf(lastCrossing);
                sb.append(String.format("Фигура %s будет отрисована после фигуры %s%n", num, otherNum));
                threads.add(new Thread(() -> drawAfter(grr, figure, lastCrossing, num, sb)));
            }
            figure.lock();
        }
        sb.append("----- Начало отрисовки кадра -----\n");
        for (Thread task : threads) {
            task.start();
        }
        for (Thread task : threads) {
            try {
                task.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        sb.append("----- Конец отрисовки кадра -----\n");

        if (editor != null) {
            editor.draw(screenConverter, g2d);
        }
        Graphics2D gr = img.createGraphics();
        gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        gr.setColor(Color.blue);
        //gr.setFont(new Font);
        drawString(gr, sb.toString(), 0, 0);

        g.drawImage(img, 0, 0, null);
    }

    private void drawIndependently(Graphics2D graphics, Drawable drawable, int num, StringBuffer sb) {
        sb.append(String.format("Фигура %d рисуется независимо%n", num));
        drawable.draw(screenConverter, graphics);
        drawable.unlock();
    }

    private void drawAfter(Graphics2D graphics, Drawable drawable, Drawable other, int num, StringBuffer sb) {
        other.lock();
        sb.append(String.format("Фигура %d отрисована %n", num));
        drawable.draw(screenConverter, graphics);
        other.unlock();
        drawable.unlock();
    }

    void drawString(Graphics g, String text, int x, int y) {
        int lineHeight = g.getFontMetrics().getHeight();
        for (String line : text.split("\n"))
            g.drawString(line, x, y += lineHeight);
    }

    private Drawable getLastCrossing(List<Drawable> figures, Drawable current) {
        Drawable lastCrossing = null;
        for (Drawable other : figures) {
            if (current == other)
                break;
            ScreenFigureBounds b1 = screenConverter.getBounds(current.getOutlinePoints());
            ScreenFigureBounds b2 = screenConverter.getBounds(other.getOutlinePoints());
            if (b2.contains(b1)) {
                lastCrossing = other;
            }
        }
        return lastCrossing;
    }

    public boolean isReversed() {
        return reversed;
    }

    public void setReversed(boolean reversed) {
        this.reversed = reversed;
    }

    public List<Drawable> getFigures() {
        return figures;
    }

    public void setFigures(List<Drawable> figures) {
        this.figures = figures;
    }

    public ScreenConverter getScreenConverter() {
        return screenConverter;
    }

    public void setScreenConverter(ScreenConverter screenConverter) {
        this.screenConverter = screenConverter;
    }

    public Editor getEditor() {
        return editor;
    }

    public Runnable getOperationCallback() {
        return operationCallback;
    }

    public void setOperationCallback(Runnable operationCallback) {
        this.operationCallback = operationCallback;
    }

    public void setEditor(Editor editor) {
        this.editor = editor;
        resetListeners();
        this.addMouseListener(editor);
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                operationCallback.run();
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        this.addMouseMotionListener(editor);
        this.addMouseWheelListener(editor);
    }

    private void resetListeners() {
        for (MouseListener listener : getMouseListeners()) {
            removeMouseListener(listener);
        }
        for (MouseMotionListener listener : getMouseMotionListeners()) {
            removeMouseMotionListener(listener);
        }
        for (MouseWheelListener listener : getMouseWheelListeners()) {
            removeMouseWheelListener(listener);
        }
    }
}