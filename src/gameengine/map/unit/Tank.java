package gameengine.map.unit;

import gameengine.enumm.GROUND;
import gameengine.enumm.UNITTYPE;

final class Tank extends AbstractUnit {

    public Tank(final int unitParty) {
        super(unitParty);
    }

    @Override
    public int getMovePoints(final GROUND underGround) {
        int movePoints;

        switch (underGround) {
        case WATER:
            movePoints = -1;
            break;
        case GRASS:
            movePoints = 1;
            break;
        case FOREST:
            movePoints = 2;
            break;
        case MOUNTAIN:
            movePoints = 2;
            break;
        case BUILDING:
            movePoints = 2;
            break;
        default:
            movePoints = -1;
        }

        return movePoints;
    }

    @Override
    public int getAttack() {
        return AbstractUnit.TANK_OFF;
    }

    @Override
    public int getMaxPower() {
        return AbstractUnit.TANK_MAXP;
    }

    @Override
    public int getMaxDistance() {
        return AbstractUnit.TANK_MAXD;
    }

    @Override
    public int getMaxFireRange() {
        return AbstractUnit.TANK_MAXF;
    }

    @Override
    public int getMinFireRange() {
        return AbstractUnit.TANK_MINF;
    }

    @Override
    public UNITTYPE getUnitType() {
        return UNITTYPE.TANK;
    }

    @Override
    public String toString() {
        return "M";
    }
}
