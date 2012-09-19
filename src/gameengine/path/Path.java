package gameengine.path;

import gameengine.enumm.DIRECTION;
import gameengine.map.Map;
import gameengine.util.Node;
import gameengine.util.Point;

public final class Path extends AbstractPath<Node> {
    Path(final Map gameMap, final Point location) {
        super(gameMap, 1, location);

        super.start();
    }

    @Override
    protected Node setNode(final Point coordinate, final DIRECTION direction, final int newStepsLeft, final Node area) {
        return new Node(direction, newStepsLeft);
    }
}
