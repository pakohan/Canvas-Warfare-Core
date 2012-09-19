package gameengine.mapevents;

import gameengine.map.Map;
import gameengine.path.Path;
import gameengine.util.Point;

public final class MapMoveEvent extends AbstractMapEvent {

    private final transient Point start, target;
    private final transient Path path;

    public MapMoveEvent(final Point startCoordinate, final Point targetCoordinate, final Path unitPath) {
        super();
        start = startCoordinate;
        target = targetCoordinate;
        path = unitPath;
    }

    public Path getPath() {
        return path;
    }

    @Override
    public String toString() {
        return "MOVE " + start.toString() + " " + target.toString();
    }

    @Override
    public AbstractMapEvent newMapEvent(final Point click, final boolean leftClick, final Map map, final int party) {
        return getMapEvent(click, leftClick, map, party);
    }

    @Override
    public MAPEVENTTYPE getType() {
        return MAPEVENTTYPE.MOVE;
    }

    @Override
    public Point getStart() {
        return start;
    }

    @Override
    public Point getTarget() {
        return target;
    }
}
