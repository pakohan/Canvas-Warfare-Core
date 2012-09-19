package gameengine.path;

import gameengine.enumm.DIRECTION;
import gameengine.enumm.GROUND;
import gameengine.map.Map;
import gameengine.util.Matrix;
import gameengine.util.Node;
import gameengine.util.Point;
import gameengine.util.Range;

import java.util.Stack;

public abstract class AbstractPath<T extends Node> implements Range {
    private static final DIRECTION[] WINDROSE = {DIRECTION.N, DIRECTION.S, DIRECTION.W, DIRECTION.E};

    private final transient int steps;
    private final transient int rounds;
    private final transient Point unitLocation;
    private final transient Matrix<T> node;
    private final transient Map map;

    public AbstractPath(final Map gameMap, final int noteableRounds, final Point location) {
        steps = gameMap.getUnit(location).getFuelLeft();
        rounds = noteableRounds;

        node = new Matrix<T>(gameMap.getDimension());
        node.fill();

        unitLocation = location;
        map = gameMap;
    }

    public final Stack<DIRECTION> getPath(final Point coordinate) {
        final Stack<DIRECTION> path = new Stack<DIRECTION>();
        Point fieldCoordinate = coordinate;
        T currentNode = getNode(coordinate);
        DIRECTION dir = currentNode.getDirection();

        do {
            if (((currentNode.getStepsLeft() / getSteps()) + 1) == rounds) {
                path.push(dir);
            }
            fieldCoordinate = fieldCoordinate.transform(Node.reverseDir(dir));
            currentNode = getNode(fieldCoordinate);
            dir = currentNode.getDirection();
        } while (dir != DIRECTION.START);

        return path;
    }

    protected final void moveUnit(final Point target, final DIRECTION dir, final int stepsLeft) {

        if (stepsLeft < 0) {
            return;
        }

        final int newStepsLeft = setNode(target, dir, stepsLeft);

        if (newStepsLeft < 1) {
            return;
        }

        for (final DIRECTION direction : WINDROSE) {
            if (dir != Node.reverseDir(direction)) {
                moveUnit(target.transform(direction), direction, newStepsLeft);
            }
        }
    }

    private int setNode(final Point coordinate, final DIRECTION direction, final int stepsLeft) {
        final GROUND ground = this.map.getGround(coordinate);
        if (ground == null) {
            return -1;
        }

        final int distance = map.getMovePoints(unitLocation, ground);
        if (distance == -1) {
            return -1;
        }

        if (map.hasUnit(coordinate) && direction != DIRECTION.START) {
            return -1;
        }

        int newStepsLeft = stepsLeft;
        if (direction != DIRECTION.START) {
            newStepsLeft -= distance;
        }

        if (newStepsLeft < 0) {
            return -1;
        }

        final T area = getNode(coordinate);
        if (area != null && area.getStepsLeft() >= newStepsLeft) {
            return -1;
        }

        node.setElement(coordinate, setNode(coordinate, direction, newStepsLeft, area));

        return newStepsLeft;
    }

    protected abstract T setNode(Point coordinate, DIRECTION direction, int newStepsLeft, T area);

    protected final T getNode(final Point coordinate) {
        return node.getElement(coordinate);
    }

    public final boolean isReachable(final Point coordinate) {
        return node.getElement(coordinate) != null && !coordinate.coordEquals(unitLocation);
    }

    public final Point getStart() {
        return unitLocation;
    }

    public final void start() {
        moveUnit(unitLocation, DIRECTION.START, getSteps() * rounds);
    }

    public final int getSteps() {
        return steps;
    }
}
