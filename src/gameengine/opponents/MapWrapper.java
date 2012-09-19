package gameengine.opponents;

import gameengine.action.ActionInterface;
import gameengine.action.Attack;
import gameengine.action.Endround;
import gameengine.action.Move;
import gameengine.map.Map;
import gameengine.mapevents.AbstractMapEvent;
import gameengine.mapevents.MapMoveEvent;
import gameengine.util.Point;

public final class MapWrapper implements AbstractOpponent {
    private transient AbstractMapEvent lastEvent = null;

    private transient int party;
    private transient Map map;
    private transient boolean isReady;
    private transient OpponentInterface core;

    public MapWrapper() {
        super();
        isReady = false;
    }

    public AbstractMapEvent click(final Point coordinate, final boolean leftClick) {
        if (isReady) {
            if (this.lastEvent == null) {
                this.lastEvent = AbstractMapEvent.getMapEvent(coordinate, leftClick, this.map, party);
            } else {
                this.lastEvent = this.lastEvent.newMapEvent(coordinate, leftClick, this.map, party);
            }

            ActionInterface action;
            switch (this.lastEvent.getType()) {
            case MOVE:
                action = new Move(this.lastEvent.getTarget(), party, ((MapMoveEvent) lastEvent).getPath());
                break;
            case ATTACK:
                action = new Attack(this.lastEvent.getStart(), this.lastEvent.getTarget(), party);
                break;
            default:
                action = null;
            }

            runAction(action);

            return this.lastEvent;
        } else {
            return null;
        }
    }

    public int getParty() {
        return party;
    }

    public void runAction(final ActionInterface action) {
        if (isReady && action != null) {
            isReady = false;
            core.dispatchAction(action);
        }
    }

    public void endRound() {
        runAction(new Endround(party));
    }

    public void startTurn(final int playerID) {
        party = playerID;
    }

    public void init(final Map gameMap, final OpponentInterface gameCore) {
        map = gameMap;
        core = gameCore;
    }

    public void setReady(final boolean bool) {
        isReady = bool;
    }
}
