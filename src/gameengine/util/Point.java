package gameengine.util;

import gameengine.enumm.DIRECTION;

public final class Point {
    private final transient int xCoord;
    private final transient int yCoord;

    public Point(final int xCoordinate, final int yCoordinate) {
        this.xCoord = xCoordinate;
        this.yCoord = yCoordinate;
    }

    public Point() {
        this.xCoord = 0;
        this.yCoord = 0;
    }

    public int getX() {
        return xCoord;
    }

    public int getY() {
        return yCoord;
    }

    public Point transform(final DIRECTION dir) {
        int xCoordinate = xCoord;
        int yCoordinate = yCoord;

        switch (dir) {
        case N:
            yCoordinate--;
            break;
        case S:
            yCoordinate++;
            break;
        case W:
            xCoordinate--;
            break;
        case E:
            xCoordinate++;
            break;
        default:
        }

        return new Point(xCoordinate, yCoordinate);
    }

    @Override
    public String toString() {
        return xCoord + ":" + yCoord;
    }

    @Override
    public int hashCode() {
        return xCoord + yCoord;
    }

    public boolean coordEquals(final Point point) {
        return point.xCoord == xCoord && point.yCoord == yCoord;
    }
}
