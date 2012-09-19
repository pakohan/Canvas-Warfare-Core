package gameengine.action;

import gameengine.enumm.UNITTYPE;
import gameengine.map.Map;
import gameengine.util.Point;

public final class CreateUnit implements ActionInterface {
    private final transient Point coordinate;
    private final transient UNITTYPE unitType;
    private final transient int unitParty;

    public CreateUnit(final Point location, final UNITTYPE type, final int party) {
        coordinate = location;
        unitType = type;
        unitParty = party;
    }

    public String serialize() {
        return "";
    }

    public boolean runAction(final Map map) {
        return map.createUnit(coordinate, unitType, unitParty);
    }

    public int getParty() {
        return unitParty;
    }
}
