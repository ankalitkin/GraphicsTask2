package ru.vsu.cs.course2.graphics;

class BresenhamEllipseUtils {
    private static double TOO_SMALL = .01;

    static int[] processEllipse(Ellipse ellipse) {
        int a = ellipse.getA();
        int b = ellipse.getB();
        int x = 0;
        int y = b;
        int[] res = new int[b + 1];
        int err = 4 * b * b * ((x + 1) * (x + 1)) + a * a * ((2 * y - 1) * (2 * y - 1)) - 4 * a * a * b * b; // Функция координат точки (x+1, y-1/2)
        while (a * a * (2 * y - 1) > 2 * b * b * (x + 1)) // Первая часть дуги
        {
            res[y] = x++;
            if (err < 0) // Переход по горизонтали
            {
                err += 4 * b * b * (2 * x + 3);
            } else // Переход по диагонали
            {
                err = err - 8 * a * a * (y - 1) + 4 * b * b * (2 * x + 3);
                y--;
            }
        }
        err = b * b * ((2 * x + 1) * (2 * x + 1)) + 4 * a * a * ((y + 1) * (y + 1)) - 4 * a * a * b * b; // Функция координат точки (x+1/2, y-1)
        while (y + 1 != 0) // Вторая часть дуги, если не выполняется условие первого цикла, значит выполняется a^2(2y - 1) <= 2b^2(x + 1)
        {
            res[y--] = x;
            if (err < 0) // Переход по вертикали
            {
                err += 4 * a * a * (2 * y + 3);
            } else // Переход по диагонали
            {
                err = err - 8 * b * b * (x + 1) + 4 * a * a * (2 * y + 3);
                x++;
            }
        }
        return res;
    }

    static boolean isPointInside(Ellipse ellipse, int x, int y) {
        if (Math.abs(ellipse.getDeltaAngle() - 2 * Math.PI) < TOO_SMALL)
            return true;

        int xc = 0;
        int yc = 0;
        int x1 = ellipse.getACosMinAngle();
        int y1 = ellipse.getBSinMinAngle();
        int x2 = ellipse.getACosMaxAngle();
        int y2 = ellipse.getBSinMaxAngle();
        double xm = ellipse.getCosMinAngle() + ellipse.getCosMaxAngle();
        double ym = ellipse.getSinMinAngle() + ellipse.getSinMaxAngle();
        double dot = xm * x + ym * y;

        if (Math.abs(dot) < TOO_SMALL)
            return (x1 - x) * (y2 - y1) < (x2 - x1) * (y1 - y);

        int a = (xc - x) * (y1 - yc) - (x1 - xc) * (yc - y);
        int b = (x2 - x) * (yc - y2) - (xc - x2) * (y2 - y);
        return (((a >= 0 && b >= 0) || (a <= 0 && b <= 0)) && (dot > 0)) == (ellipse.getDeltaAngle() < Math.PI);
    }
}
