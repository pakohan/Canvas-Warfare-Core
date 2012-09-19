package gameengine.map.unit;

import gameengine.enumm.GROUND;
import gameengine.enumm.UNITTYPE;

final class Horwitzer extends AbstractUnit {

    public Horwitzer(final int unitParty) {
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
        return AbstractUnit.HORWITZER_OFF;
    }

    @Override
    public int getMaxPower() {
        return AbstractUnit.HORWITZER_MAXP;
    }

    @Override
    public int getMaxDistance() {
        return AbstractUnit.HORWITZER_MAXD;
    }

    @Override
    public int getMaxFireRange() {
        return AbstractUnit.HORWITZER_MAXF;
    }

    @Override
    public int getMinFireRange() {
        return AbstractUnit.HORWITZER_MINF;
    }

    @Override
    public UNITTYPE getUnitType() {
        return UNITTYPE.HORWITZER;
    }

    @Override
    public String toString() {
        return "H";
    }
}
