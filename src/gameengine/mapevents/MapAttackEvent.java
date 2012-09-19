package gameengine.mapevents;

import gameengine.map.Map;
import gameengine.util.Point;

public final class MapAttackEvent extends AbstractMapEvent {

    private final transient Point attacker, target;

    public MapAttackEvent(final Point attackerLocation, final Point targetLocation) {
        super();
        attacker = attackerLocation;
        target = targetLocation;
    }

    @Override
    public MAPEVENTTYPE getType() {
        return MAPEVENTTYPE.ATTACK;
    }

    @Override
    public String toString() {
        return "ATTACK " + attacker.toString() + " " + target.toString();
    }

    @Override
    public AbstractMapEvent newMapEvent(final Point click, final boolean leftClick, final Map map, final int party) {
        return getMapEvent(click, leftClick, map, party);
    }

    @Override
    public Point getStart() {
        return attacker;
    }

    @Override
    public Point getTarget() {
        return target;
    }
}
