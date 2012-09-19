package gameengine.util;

import gameengine.enumm.DIRECTION;

public class Node {
    private final transient DIRECTION direction;
    private final transient int stepsLeft;

    public Node(final DIRECTION dir, final int steps) {
        direction = dir;
        stepsLeft = steps;
    }

    public final DIRECTION getDirection() {
        return direction;
    }

    @Override
    public final String toString() {
        return this.direction + "|" + getStepsLeft();
    }

    public final int getStepsLeft() {
        return stepsLeft;
    }

    public static DIRECTION reverseDir(final DIRECTION dir) {
        DIRECTION returnVal;

        switch (dir) {
        case N:
            returnVal = DIRECTION.S;
            break;
        case S:
            returnVal = DIRECTION.N;
            break;
        case W:
            returnVal = DIRECTION.E;
            break;
        case E:
            returnVal = DIRECTION.W;
            break;
        default:
            returnVal = DIRECTION.START;
        }

        return returnVal;
    }
}
