package gameengine.map.unit;

import gameengine.enumm.GROUND;
import gameengine.enumm.UNITTYPE;

final class Heli extends AbstractUnit {

    public Heli(final int unitParty) {
        super(unitParty);
    }

    @Override
    public int getMovePoints(final GROUND underGround) {
        return 1;
    }

    @Override
    public int getAttack() {
        return AbstractUnit.HELI_OFF;
    }

    @Override
    public int getMaxPower() {
        return AbstractUnit.HELI_MAXP;
    }

    @Override
    public int getMaxDistance() {
        return AbstractUnit.HELI_MAXD;
    }

    @Override
    public int getMaxFireRange() {
        return AbstractUnit.HELI_MAXF;
    }

    @Override
    public int getMinFireRange() {
        return AbstractUnit.HELI_MINF;
    }

    @Override
    public UNITTYPE getUnitType() {
        return UNITTYPE.HELI;
    }

    @Override
    public String toString() {
        return "_";
    }
}
