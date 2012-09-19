package gameengine.opponents;

import gameengine.action.ActionInterface;
import gameengine.action.Attack;
import gameengine.action.Endround;
import gameengine.action.Move;
import gameengine.enumm.DIRECTION;
import gameengine.map.Map;
import gameengine.path.FireRange;
import gameengine.util.Point;

import java.util.List;
import java.util.Stack;

public final class KI implements AbstractOpponent {
    private static final int ROUNDS = 10;

    private transient Map map;
    private transient OpponentInterface core;

    public KI() {
        super();
    }

    public void startTurn(final int playerID) {
        new WorkerThread(playerID);
    }

    public void init(final Map gameMap, final OpponentInterface gameCore) {
        map = gameMap;
        core = gameCore;
    }

    public void setReady(final boolean bool) {
    }

    private final class WorkerThread extends java.lang.Thread {
        private final transient int party;
        private final transient Map currentMap;

        public WorkerThread(final int playerID) {
            party = playerID;
            currentMap = map.trueClone();
            super.start();
        }

        @Override
        public void run() {
            final List<Point> unitLocations = currentMap.getUnits(party);

            for (final Point unit : unitLocations) {
                final Point point = moveUnit(unit);
                if (point != null) {
                    final FireRange fire = new FireRange(point, currentMap.getUnit(point), currentMap);
                    if (fire.getAttackableUnits() > 0) {
                        final Point attackLocation = fire.getAttackbleField();
                        currentMap.attackUnit(point, attackLocation);

                        final ActionInterface attack = new Attack(point, attackLocation, party);
                        core.dispatchAction(attack);
                    }
                }
            }

            final ActionInterface endRound = new Endround(party);
            core.dispatchAction(endRound);
        }

        private Point moveUnit(final Point unitCoordinate) {
            final KIPath path = new KIPath(currentMap, 10, unitCoordinate);
            int i;
            for (i = 0; i < ROUNDS; i++) {
                if (path.getBestMoveRating(i) > 0) {
                    break;
                }
            }

            if (i < ROUNDS) {
                final ActionInterface move = new Move(path.getBestMove(i), party, path);
                core.dispatchAction(move);

                final Stack<DIRECTION> pathOrders = path.getPath(path.getBestMove(i));

                Point start = path.getStart();

                while (!pathOrders.empty()) {
                    start = currentMap.moveUnit(start, pathOrders.pop());
                }

                return start;
            } else {
                return null;
            }
        }
    }
}
