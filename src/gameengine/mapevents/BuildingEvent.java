package gameengine.mapevents;

import gameengine.map.Map;
import gameengine.util.Point;

final class BuildingEvent extends AbstractMapEvent {
    private final transient Point location;

    BuildingEvent(final Point coordinate) {
        super();
        location = coordinate;
    }

    @Override
    public MAPEVENTTYPE getType() {
        return MAPEVENTTYPE.BUILDING;
    }

    @Override
    public Point getStart() {
        return location;
    }

    @Override
    public Point getTarget() {
        return null;
    }

    @Override
    public AbstractMapEvent newMapEvent(final Point click, final boolean leftClick, final Map map, final int party) {
        return getMapEvent(click, leftClick, map, party);
    }
}
