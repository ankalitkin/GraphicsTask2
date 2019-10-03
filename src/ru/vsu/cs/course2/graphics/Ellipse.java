package ru.vsu.cs.course2.graphics;

class Ellipse {
    private int x1, y1, x2, y2, xc, yc, a, b, ACosMinAngle, ACosMaxAngle, BSinMinAngle, BSinMaxAngle;
    private double minAngle, maxAngle, deltaAngle,
            sinMinAngle, sinMaxAngle, cosMinAngle, cosMaxAngle;
    private int[] processed;

    Ellipse(int x1, int y1, int x2, int y2, double startAngle, double endAngle) {
        if (x1 > x2) {
            int tmp = x1;
            x1 = x2;
            x2 = tmp;
        }
        if (y1 > y2) {
            int tmp = y1;
            y1 = y2;
            y2 = tmp;
        }
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        a = (x2 - x1) / 2;
        b = (y2 - y1) / 2;
        xc = (x1 + x2) / 2;
        yc = (y1 + y2) / 2;
        minAngle = Math.min(startAngle, endAngle);
        maxAngle = Math.max(startAngle, endAngle);
        deltaAngle = maxAngle - minAngle;
        sinMinAngle = Math.sin(minAngle);
        sinMaxAngle = Math.sin(maxAngle);
        cosMinAngle = Math.cos(minAngle);
        cosMaxAngle = Math.cos(maxAngle);
        ACosMaxAngle = (int) (a * cosMaxAngle);
        ACosMinAngle = (int) (a * cosMinAngle);
        BSinMaxAngle = (int) (b * sinMaxAngle);
        BSinMinAngle = (int) (b * sinMinAngle);
        processed = BresenhamEllipseUtils.processEllipse(this);
    }

    int getX1() {
        return x1;
    }

    int getY1() {
        return y1;
    }

    int getX2() {
        return x2;
    }

    int getY2() {
        return y2;
    }

    int getXc() {
        return xc;
    }

    int getYc() {
        return yc;
    }

    int getA() {
        return a;
    }

    int getB() {
        return b;
    }

    double getMinAngle() {
        return minAngle;
    }

    double getMaxAngle() {
        return maxAngle;
    }

    double getSinMinAngle() {
        return sinMinAngle;
    }

    double getSinMaxAngle() {
        return sinMaxAngle;
    }

    double getCosMinAngle() {
        return cosMinAngle;
    }

    double getCosMaxAngle() {
        return cosMaxAngle;
    }

    double getDeltaAngle() {
        return deltaAngle;
    }

    int getACosMinAngle() {
        return ACosMinAngle;
    }

    int getACosMaxAngle() {
        return ACosMaxAngle;
    }

    int getBSinMinAngle() {
        return BSinMinAngle;
    }

    int getBSinMaxAngle() {
        return BSinMaxAngle;
    }

    public int[] getProcessed() {
        return processed;
    }
}