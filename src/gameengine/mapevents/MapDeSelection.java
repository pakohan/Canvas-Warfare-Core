package gameengine.mapevents;

import gameengine.map.Map;
import gameengine.util.Point;

public final class MapDeSelection extends AbstractMapEvent {

    private final transient Point location;

    public MapDeSelection(final Point coordinate) {
        super();
        location = coordinate;
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
    public MAPEVENTTYPE getType() {
        return MAPEVENTTYPE.DESELECT;
    }

    @Override
    public AbstractMapEvent newMapEvent(final Point click, final boolean leftClick, final Map map, final int party) {
        return getMapEvent(click, leftClick, map, party);
    }

}
