package gameengine.mapevents;

import gameengine.map.Map;
import gameengine.util.Point;

public interface MapEventInterface {
    public enum MAPEVENTTYPE {
        SELECT, DESELECT, MOVE, ATTACK, FIRERANGE, BUILDING
    }

    MapEventInterface newMapEvent(Point click, boolean leftClick, Map map, int party);

    MAPEVENTTYPE getType();

    Point getStart();

    Point getTarget();

}
