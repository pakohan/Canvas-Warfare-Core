package gameengine.map.unit;

import gameengine.enumm.GROUND;
import gameengine.enumm.UNITTYPE;

final class Soldier extends AbstractUnit {

    public Soldier(final int unitParty) {
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
            movePoints = 1;
            break;
        default:
            movePoints = -1;
        }

        return movePoints;
    }

    @Override
    public int getAttack() {
        return AbstractUnit.SOLDIER_OFF;
    }

    @Override
    public int getMaxPower() {
        return AbstractUnit.SOLDIER_MAXP;
    }

    @Override
    public int getMaxDistance() {
        return AbstractUnit.SOLDIER_MAXD;
    }

    @Override
    public int getMaxFireRange() {
        return AbstractUnit.SOLDIER_MAXF;
    }

    @Override
    public int getMinFireRange() {
        return AbstractUnit.SOLDIER_MINF;
    }

    @Override
    public UNITTYPE getUnitType() {
        return UNITTYPE.SOLDIER;
    }

    @Override
    public String toString() {
        return "T";
    }
}
