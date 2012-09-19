package gameengine.action;

import gameengine.map.Map;
import gameengine.util.Point;

public final class ConquerBuilding implements ActionInterface {
    private final transient Point buildingCoordinate;
    private final transient int newParty;

    public ConquerBuilding(final Point coordinate, final int party) {
        buildingCoordinate = coordinate;
        newParty = party;
    }

    public String serialize() {
        return "";
    }

    public boolean runAction(final Map map) {
        return map.conquer(buildingCoordinate, newParty);
    }

    public int getParty() {
        return newParty;
    }
}
