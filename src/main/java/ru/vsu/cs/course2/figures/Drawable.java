package ru.vsu.cs.course2.figures;

import lombok.SneakyThrows;
import ru.vsu.cs.course2.ScreenConverter;

import java.awt.*;
import java.util.List;
import java.util.concurrent.Semaphore;

public abstract class Drawable {
    private Semaphore myLock;

    public abstract List<RealPoint> getOutlinePoints();

    public abstract void draw(ScreenConverter screenConverter, Graphics2D g);

    public Semaphore getSyncObject() {
        if (myLock == null)
            myLock = new Semaphore(1);
        return myLock;
    }

    @SneakyThrows
    public void lock() {
        getSyncObject().acquire();
    }

    public void unlock() {
        getSyncObject().release();
    }
}
