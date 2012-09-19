package gameengine.opponents;

import gameengine.enumm.DIRECTION;
import gameengine.map.Map;
import gameengine.path.AbstractPath;
import gameengine.path.FireRange;
import gameengine.util.Point;

public final class KIPath extends AbstractPath<KINode> {
    private final transient Point[] bestMoves;
    private final transient Point unitLocation;
    private final transient Map map;

    public KIPath(final Map gameMap, final int noteableRounds, final Point location) {
        super(gameMap, noteableRounds, location);

        bestMoves = new Point[noteableRounds];
        map = gameMap;
        unitLocation = location;

        super.start();
    }

    @Override
    protected KINode setNode(final Point coordinate, final DIRECTION direction, final int newStepsLeft, final KINode area) {
        KINode newNode;
        if (area == null) {
            newNode = new KINode(direction, newStepsLeft, rateField(coordinate));
        } else {
            newNode = new KINode(direction, newStepsLeft, area.getRating());
        }

        if (direction != DIRECTION.START) {
            setBestMove(coordinate, newStepsLeft, newNode.getRating());
        }

        return newNode;
    }

    private void setBestMove(final Point coordinate, final int stepsLeft, final int rating) {
        final int index = (bestMoves.length - 1) - (stepsLeft / getSteps());

        if (bestMoves[index] == null || getNode(bestMoves[index]).getRating() < rating) {
            bestMoves[index] = coordinate;
        }
    }

    private int rateField(final Point coordinate) {
        final FireRange range = new FireRange(coordinate, map.getUnit(unitLocation), map);

        return range.getAttackableUnits();
    }

    public Point getBestMove(final int round) {
        return bestMoves[round];
    }

    public int getBestMoveRating(final int round) {
        return getNode(bestMoves[round]).getRating();
    }
}
