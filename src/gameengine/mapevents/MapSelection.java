package gameengine.mapevents;

import gameengine.enumm.GROUND;
import gameengine.map.Map;
import gameengine.path.Path;
import gameengine.path.PathFactory;
import gameengine.util.Point;

public final class MapSelection extends AbstractMapEvent {

    private final transient Point location;
    private transient Path path;

    public MapSelection(final Point coordinate) {
        super();
        location = coordinate;
    }

    @Override
    public MAPEVENTTYPE getType() {
        return MAPEVENTTYPE.SELECT;
    }

    @Override
    public AbstractMapEvent newMapEvent(final Point click, final boolean leftClick, final Map map, final int party) {
        final GROUND ground = map.getGround(click);

        AbstractMapEvent event;

        if (!map.hasUnit(click)) {
            getPath(map);
            if (path.isReachable(click)) {
                event = new MapMoveEvent(location, click, path);
            } else {
                if (ground == GROUND.BUILDING && map.getFieldParty(click) == party) {
                    event = new BuildingEvent(click);
                } else {
                    event = new MapDeSelection(click);
                }
            }
        } else {
            if (map.getUnit(click).getParty() == party) {
                if (leftClick) {
                    if (location.coordEquals(click)) {
                        event = new MapDeSelection(click);
                    } else {
                        event = new MapSelection(click);
                    }
                } else {
                    event = new MapFireRangeSelection(click);
                }
            } else {
                event = new MapDeSelection(click);
            }
        }
        return event;
    }

    public Path getPath(final Map map) {
        if (path == null) {
            path = PathFactory.getPath(location, map);
        }
        return path;
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
