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
        System.out.println("----- Начало отрисовки кадра -----");
        for (Drawable figure : drawList) {
            int cv = counter;
            Thread myDrawingThread = new Thread(() -> {
                Graphics2D grr = img.createGraphics();
                grr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Drawable lastCrossing = getLastCrossing(drawList, figure);
                if (lastCrossing == null) {
                    synchronized (figure.getSyncObject()) {
                        System.out.printf("Фигура %d рисуется независимо%n", cv);
                        figure.draw(screenConverter, grr);
                    }
                } else {
                    System.out.printf("Фигура %d будет отрисована после фигуры %d%n",
                            cv, drawList.indexOf(lastCrossing));
                    synchronized (lastCrossing.getSyncObject()) {
                        synchronized (figure.getSyncObject()) {
                            figure.draw(screenConverter, grr);
                            System.out.printf("Фигура %d отрисована%n", cv);
                        }
                    }
                    //Косяк - объект блокируется только на время отрисовки, а не сразу.
                    //Это нужно как-то исправить. Нужно блокировать его сразу при создании потока
                }
            });
            counter++;
            myDrawingThread.start();
            threads.add(myDrawingThread);
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException ignored) {
            }
        }
        System.out.printf("----- Конец отрисовки кадра -----%n%n");

        if (editor != null) {
            editor.draw(screenConverter, g2d);
        }
        g.drawImage(img, 0, 0, null);
    }

    private Drawable getLastCrossing(List<Drawable> figures, Drawable current) {
        Drawable lastCrossing = null;
        for (Drawable other : figures) {
            if (current == other)
                break;
            ScreenFigureBounds b1 = screenConverter.getBounds(current.getOutlinePoints());
            ScreenFigureBounds b2 = screenConverter.getBounds(other.getOutlinePoints());
            if (b1.crossesWith(b2)) {
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