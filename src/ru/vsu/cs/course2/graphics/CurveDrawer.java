package ru.vsu.cs.course2.graphics;

import ru.vsu.cs.course2.Curve;
import ru.vsu.cs.course2.Vector2;

import java.util.List;

public class CurveDrawer {
    private GraphicsProvider graphicsProvider;

    public CurveDrawer(GraphicsProvider graphicsProvider) {
        this.graphicsProvider = graphicsProvider;
    }

    public void drawCurve(List<Vector2> keyPoints) {
        Curve curve = new Curve(keyPoints);
        List<Vector2> points = curve.getPointsProcessed();
        PolyLineDrawer polyLineDrawer = graphicsProvider.getPolyLineDrawer();
        polyLineDrawer.drawRealLine(points);
    }
}
