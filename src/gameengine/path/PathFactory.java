package gameengine.path;

import gameengine.map.Map;
import gameengine.util.Point;

public final class PathFactory {
    private PathFactory() {
    }

    public static Path getPath(final Point startCoordinate, final Map gameMap) {
        return new Path(gameMap, startCoordinate);
    }
}
