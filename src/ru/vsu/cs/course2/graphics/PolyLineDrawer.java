package ru.vsu.cs.course2.graphics;

import ru.vsu.cs.course2.Vector2;

import java.util.List;

public class PolyLineDrawer {
    private GraphicsProvider graphicsProvider;

    public PolyLineDrawer(GraphicsProvider graphicsProvider) {
        this.graphicsProvider = graphicsProvider;
    }

    public void drawRealLine(List<Vector2> realPoints) {
        RealLineDrawer ld = graphicsProvider.getRealLineDrawer();
        Vector2 lastPoint = null;
        for (Vector2 point : realPoints) {
            if (lastPoint != null) {
                ld.drawLine(lastPoint, point);
            }
            lastPoint = point;
        }
    }
}
