package ru.vsu.cs.course2.graphics;

class BresenhamEllipseUtils {
    private static final double TOO_SMALL = .01;

    static int[] processEllipse(Ellipse ellipse) {
        int a = ellipse.getA();
        int b = ellipse.getB();
        int x = 0;
        int y = b;
        int[] res = new int[b + 1];
        int err = 4 * b * b * ((x + 1) * (x + 1)) + a * a * ((2 * y - 1) * (2 * y - 1)) - 4 * a * a * b * b; // Функция координат точки (x+1, y-1/2)
        while (a * a * (2 * y - 1) > 2 * b * b * (x + 1)) { // Первая часть дуги
            res[y] = x++;
            if (err < 0) { // Переход по горизонтали
                err += 4 * b * b * (2 * x + 3);
            } else { // Переход по диагонали
                err = err - 8 * a * a * (y - 1) + 4 * b * b * (2 * x + 3);
                y--;
            }
        }
        err = b * b * ((2 * x + 1) * (2 * x + 1)) + 4 * a * a * ((y + 1) * (y + 1)) - 4 * a * a * b * b; // Функция координат точки (x+1/2, y-1)
        while (y + 1 != 0) { // Вторая часть дуги, если не выполняется условие первого цикла, значит выполняется a^2(2y - 1) <= 2b^2(x + 1)
            res[y--] = x;
            if (err < 0) { // Переход по вертикали
                err += 4 * a * a * (2 * y + 3);
            } else {  // Переход по диагонали
                err = err - 8 * b * b * (x + 1) + 4 * a * a * (2 * y + 3);
                x++;
            }
        }
        return res;
    }

    static boolean isPointInside(Ellipse ellipse, int x, int y) {
        if (ellipse.getDeltaAngle() < TOO_SMALL)
            return false;
        if (Math.abs(ellipse.getDeltaAngle() - 2 * Math.PI) < TOO_SMALL)
            return true;

        double angle = (Math.atan2(y / (double) ellipse.getB(), x / (double) ellipse.getA()) + Math.PI * 2) % (Math.PI * 2);
        return ellipse.getMinAngle() <= angle && angle <= ellipse.getMaxAngle()
                || ellipse.getMinAngle() <= angle + Math.PI * 2 && angle + Math.PI * 2 <= ellipse.getMaxAngle();
    }
}
