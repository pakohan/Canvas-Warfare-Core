package gameengine;

import gameengine.action.ActionInterface;
import gameengine.action.Endround;
import gameengine.map.Map;
import gameengine.opponents.AbstractOpponent;
import gameengine.opponents.OpponentInterface;
import gameengine.util.Logger;

import java.io.FileNotFoundException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public final class Core extends java.lang.Thread implements OpponentInterface {
    private static final int MAXQUEUE = 20;

    private final transient Map map;
    private final transient AbstractOpponent[] opponents;

    private final transient BlockingQueue<ActionInterface> opponentActions;

    private transient int round;

    public Core(final MapProperty gamemap) throws FileNotFoundException {
        super();
        map = gamemap.getMap();
        opponents = new AbstractOpponent[gamemap.getNumberPlayers()];
        round = -1;
        opponentActions = new ArrayBlockingQueue<ActionInterface>(MAXQUEUE);
    }

    public void setOpponent(final AbstractOpponent opponent, final int party) {
        if (opponents[party - 1] == null) {
            opponents[party - 1] = opponent;
            opponent.init(map, this);
        } else {
            throw new IllegalArgumentException("Party already taken");
        }
    }

    public void startGame() {
        for (int i = 0; i < opponents.length; i++) {
            if (opponents[i] == null) {
                throw new IllegalStateException("Party " + (i + 1) + " not set yet!");
            }
        }

        super.start();
    }

    public Map getMap() {
        return map;
    }

    public void dispatchAction(final ActionInterface action) {
        final int playerID = action.getParty() - 1;
        if (playerID == round % opponents.length) {
            try {
                opponentActions.put(action);
            } catch (final InterruptedException e) {
                Logger.debug("Core.dispatchAction()", e.getMessage());
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            round++;
            try {
                ActionInterface action;
                final int playerID = round % opponents.length;
                opponents[playerID].startTurn(playerID + 1);
                do {
                    opponents[playerID].setReady(true);
                    action = opponentActions.take();
                    action.runAction(map);
                } while (!(action instanceof Endround));
            } catch (final InterruptedException e) {
                Logger.debug("Core.run()", e.getMessage());
            }

        }
    }
}
