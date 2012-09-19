package gameengine.mapevents;

import gameengine.enumm.GROUND;
import gameengine.map.Map;
import gameengine.util.Point;

public abstract class AbstractMapEvent {
    public enum MAPEVENTTYPE {
        SELECT, DESELECT, MOVE, ATTACK, FIRERANGE, BUILDING
    }

    public static final AbstractMapEvent getMapEvent(final Point click, final boolean leftClick, final Map map, final int party) {
        final GROUND ground = map.getGround(click);

        AbstractMapEvent currentEvent;

        if (leftClick) {
            if (!map.hasUnit(click) || map.getUnit(click).getParty() != party) {
                if (ground == GROUND.BUILDING && map.getFieldParty(click) == party) {
                    currentEvent = new BuildingEvent(click);
                } else {
                    currentEvent = new MapDeSelection(click);
                }
            } else {
                currentEvent = new MapSelection(click);
            }
        } else {
            if (!map.hasUnit(click) || map.getUnit(click).getParty() != party) {
                if (ground == GROUND.BUILDING) {
                    currentEvent = new BuildingEvent(click);
                } else {
                    currentEvent = new MapDeSelection(click);
                }
            } else {
                currentEvent = new MapFireRangeSelection(click);
            }
        }

        return currentEvent;
    }

    public abstract AbstractMapEvent newMapEvent(final Point click, final boolean leftClick, final Map map, final int party);

    public abstract MAPEVENTTYPE getType();

    public abstract Point getStart();

    public abstract Point getTarget();
}
