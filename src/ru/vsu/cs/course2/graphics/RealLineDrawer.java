package ru.vsu.cs.course2.graphics;

import ru.vsu.cs.course2.ScreenConverter;
import ru.vsu.cs.course2.ScreenPoint;
import ru.vsu.cs.course2.Vector2;

public class RealLineDrawer {
    private GraphicsProvider graphicsProvider;

    public RealLineDrawer(GraphicsProvider graphicsProvider) {
        this.graphicsProvider = graphicsProvider;
    }

    public void drawLine(Vector2 v1, Vector2 v2) {
        ScreenConverter sc = graphicsProvider.getScreenConverter();
        ScreenPoint p1 = sc.realToScreen(v1);
        ScreenPoint p2 = sc.realToScreen(v2);
        LineDrawer lineDrawer = graphicsProvider.getLineDrawer();
        lineDrawer.drawLine(p1, p2);
    }

}
