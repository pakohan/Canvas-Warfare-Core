package gameengine.mapevents;

import gameengine.enumm.GROUND;
import gameengine.map.Map;
import gameengine.path.FireRange;
import gameengine.util.Point;
import gameengine.util.Range;

public final class MapFireRangeSelection extends AbstractMapEvent {
    private final transient Point location;

    private transient FireRange fireRange;

    public MapFireRangeSelection(final Point coordinate) {
        super();
        location = coordinate;
    }

    @Override
    public MAPEVENTTYPE getType() {
        return MAPEVENTTYPE.FIRERANGE;
    }

    @Override
    public AbstractMapEvent newMapEvent(final Point click, final boolean leftClick, final Map map, final int party) {
        final GROUND ground = map.getGround(click);

        AbstractMapEvent returnVal;

        if (!map.hasUnit(click)) {
            if (ground == GROUND.BUILDING && map.getFieldParty(click) == party) {
                returnVal = new BuildingEvent(click);
            } else {
                returnVal = new MapDeSelection(click);
            }
        } else {
            AbstractMapEvent event = new MapSelection(click);
            if (leftClick) {
                event = new MapSelection(click);
            } else {
                if (location.coordEquals(click)) {
                    returnVal = new MapDeSelection(click);
                } else {
                    returnVal = new MapSelection(click);
                }
            }
            if (map.getUnit(click).getParty() == party) {
                returnVal = event;
            } else {
                getFireRange(map);
                if (fireRange.isReachable(click)) {
                    returnVal = new MapAttackEvent(location, click);
                } else {
                    returnVal = new MapDeSelection(click);
                }
            }
        }

        return returnVal;
    }

    public Range getFireRange(final Map map) {
        if (fireRange == null) {
            fireRange = new FireRange(location, map.getUnit(location), map);
        }

        return fireRange;
    }

    @Override
    public Point getStart() {
        return location;
    }

    @Override
    public Point getTarget() {
        return null;
    }
}
