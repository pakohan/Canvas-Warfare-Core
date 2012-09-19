package gameengine.map.unit;

import gameengine.enumm.UNITTYPE;

public final class UnitFactory {
    private UnitFactory() {
    }

    public static AbstractUnit createUnit(final UNITTYPE type, final int unitParty) {
        AbstractUnit unit;

        switch (type) {
        case TANK:
            unit = new Tank(unitParty);
            break;
        case HELI:
            unit = new Heli(unitParty);
            break;
        case SOLDIER:
            unit = new Soldier(unitParty);
            break;
        case HORWITZER:
            unit = new Horwitzer(unitParty);
            break;
        default:
            unit = null;
        }

        return unit;
    }
}
