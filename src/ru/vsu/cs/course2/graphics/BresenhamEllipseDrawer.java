package ru.vsu.cs.course2.graphics;

import java.awt.*;

public class BresenhamEllipseDrawer implements EllipseDrawer {
    private GraphicsProvider graphicsProvider;
    private static final int DELTA = 1;

    public BresenhamEllipseDrawer(GraphicsProvider graphicsProvider) {
        this.graphicsProvider = graphicsProvider;
    }

    @Override
    public void drawEllipse(Graphics2D graphics, int x1, int y1, int x2, int y2) {
        drawArc(graphics, x1, y1, x2, y2, 0, 2 * Math.PI);
    }

    @Override
    public void drawArc(Graphics2D graphics, int x1, int y1, int x2, int y2, double startAngle, double endAngle) {
        Ellipse ellipse = new Ellipse(x1, y1, x2, y2, startAngle, endAngle);
        drawEllipse(graphics, ellipse);
    }

    private void drawEllipse(Graphics2D graphics, Ellipse ellipse) {
        int a = ellipse.getA();
        int b = ellipse.getB();
        int x = 0; // Компонента x
        int y = b; // Компонента y
        int err = 4 * b * b * ((x + 1) * (x + 1)) + a * a * ((2 * y - 1) * (2 * y - 1)) - 4 * a * a * b * b; // Функция координат точки (x+1, y-1/2)
        while (a * a * (2 * y - 1) > 2 * b * b * (x + 1)) // Первая часть дуги
        {
            drawEllipsePixel(graphics, ellipse, x, y);
            if (err < 0) // Переход по горизонтали
            {
                x++;
                err += 4 * b * b * (2 * x + 3);
            } else // Переход по диагонали
            {
                x++;
                err = err - 8 * a * a * (y - 1) + 4 * b * b * (2 * x + 3);
                y--;
            }
        }
        err = b * b * ((2 * x + 1) * (2 * x + 1)) + 4 * a * a * ((y + 1) * (y + 1)) - 4 * a * a * b * b; // Функция координат точки (x+1/2, y-1)
        while (y + 1 != 0) // Вторая часть дуги, если не выполняется условие первого цикла, значит выполняется a^2(2y - 1) <= 2b^2(x + 1)
        {
            drawEllipsePixel(graphics, ellipse, x, y);
            if (err < 0) // Переход по вертикали
            {
                y--;
                err += 4 * a * a * (2 * y + 3);
            } else // Переход по диагонали
            {
                y--;
                err = err - 8 * b * b * (x + 1) + 4 * a * a * (2 * y + 3);
                x++;
            }
        }
    }

    private boolean isBetween(double angle, double start, double end) {
        double pi2 = Math.PI * 2;
        angle %= pi2;
        boolean res = start <= angle && angle <= end;
        angle += +pi2;
        res |= start <= angle && angle <= end;
        return res;
    }

    private void drawEllipsePixel(Graphics2D graphics, Ellipse ellipse, int x, int y) {
        checkAndDrawPixel(graphics, ellipse, x, y);
        checkAndDrawPixel(graphics, ellipse, -x, y);
        checkAndDrawPixel(graphics, ellipse, -x, -y);
        checkAndDrawPixel(graphics, ellipse, x, -y);
    }

    private void checkAndDrawPixel(Graphics2D graphics, Ellipse ellipse, int x, int y) {
        int a = ellipse.getA();
        int b = ellipse.getB();
        double minCos = ellipse.getCosMinAngle();
        double maxCos = ellipse.getCosMaxAngle();
        double minSin = ellipse.getSinMinAngle();
        double maxSin = ellipse.getSinMaxAngle();
        double left = -a - DELTA;
        double right = a + DELTA;
        double bottom = -b - DELTA;
        double top = b + DELTA;
        double minAngle = ellipse.getMinAngle();
        double maxAngle = ellipse.getMaxAngle();
        boolean drawInside = maxAngle - minAngle < Math.PI;

        if (isBetween(Math.PI, minAngle, maxAngle) != drawInside) {
            left = Math.floor(a * Math.min(minCos, maxCos)) - DELTA;
        }
        if (isBetween(0, minAngle, maxAngle) != drawInside) {
            right = Math.ceil(a * Math.max(minCos, maxCos)) + DELTA;
        }
        if (isBetween(Math.PI * 3 / 2, minAngle, maxAngle) != drawInside) {
            bottom = Math.floor(b * Math.min(minSin, maxSin)) - DELTA;
        }
        if (isBetween(Math.PI / 2, minAngle, maxAngle) != drawInside) {
            top = Math.ceil(b * Math.max(minSin, maxSin)) + DELTA;
        }

        boolean pointInside = (left <= x && x <= right) && (bottom <= y && y <= top);
        boolean workaround = pointInside && (top - bottom < 3 || right - left < 3);
        if (drawInside == pointInside || workaround) {
            graphicsProvider.getPixelDrawer().drawPixel(graphics, ellipse.getXc() + x, ellipse.getYc() + y);
        }

        //graphics.setColor(Color.green);
        //graphics.drawRect((int) (ellipse.getXc() + left), (int) (ellipse.getYc() + bottom), (int) (right - left), (int) (top - bottom));
    }
}
