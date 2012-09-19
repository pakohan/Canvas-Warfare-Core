package gameengine.action;

import gameengine.enumm.DIRECTION;
import gameengine.enumm.UNITTYPE;
import gameengine.map.Map;
import gameengine.path.AbstractPath;
import gameengine.sound.SoundServer;
import gameengine.util.Logger;
import gameengine.util.Node;
import gameengine.util.Point;
import gameengine.util.VerifyInstallation;

import java.util.Stack;

public final class Move implements ActionInterface {
    private final transient Point target;
    private final transient int party;
    private final transient AbstractPath<? extends Node> path;
    public static final long DURATION = 200;

    public Move(final Point targetCoordinate, final int unitParty, final AbstractPath<? extends Node> unitPath) {
        super();

        target = targetCoordinate;

        this.party = unitParty;
        this.path = unitPath;

        if (path.getStart().coordEquals(targetCoordinate)) {
            throw new IllegalArgumentException("Start equals Target");
        }
    }

    public String serialize() {
        return "MOVE " + path.getStart() + " " + target;
    }

    public void pause(final long mili) {
        try {
            Thread.sleep(mili);
        } catch (final InterruptedException e) {
            Logger.debug("Move.pause()", e.getMessage());
        }
    }

    public boolean runAction(final Map map) {

        final Stack<DIRECTION> pathOrders = path.getPath(target);

        final SoundServer sound = VerifyInstallation.getSoundServer();
        Point start = path.getStart();
        final UNITTYPE type = map.getUnit(start).getUnitType();
        sound.startMoveSound(type);

        while (!pathOrders.empty()) {
            start = map.moveUnit(start, pathOrders.pop());
            pause(DURATION);
        }

        sound.stopMoveSound(type);

        return true;
    }

    public int getParty() {
        return this.party;
    }
}
