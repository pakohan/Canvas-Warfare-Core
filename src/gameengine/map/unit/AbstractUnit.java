package gameengine.map.unit;

import gameengine.enumm.DIRECTION;
import gameengine.enumm.GROUND;
import gameengine.enumm.UNITTYPE;
import gameengine.util.Element;
import gameengine.util.ReallyCloneable;
import gameengine.util.StringResources;
import gameengine.util.StringResources.MAPATTRIBUTES;

public abstract class AbstractUnit implements ReallyCloneable<AbstractUnit> {
    protected static final int TANK_MAXP = 30;
    protected static final int HELI_MAXP = 25;
    protected static final int SOLDIER_MAXP = 20;
    protected static final int HORWITZER_MAXP = 50;

    protected static final int TANK_MAXD = 10;
    protected static final int HELI_MAXD = 12;
    protected static final int SOLDIER_MAXD = 5;
    protected static final int HORWITZER_MAXD = 7;

    protected static final int TANK_MAXF = 1;
    protected static final int HELI_MAXF = 1;
    protected static final int SOLDIER_MAXF = 1;
    protected static final int HORWITZER_MAXF = 5;

    protected static final int TANK_MINF = 0;
    protected static final int HELI_MINF = 0;
    protected static final int SOLDIER_MINF = 0;
    protected static final int HORWITZER_MINF = 3;

    protected static final int TANK_OFF = 5;
    protected static final int HELI_OFF = 4;
    protected static final int SOLDIER_OFF = 3;
    protected static final int HORWITZER_OFF = 7;

    private final transient int party;
    private transient int power;
    private transient int fuel;
    private transient boolean ableToAttack = true;

    private transient DIRECTION dir;

    public AbstractUnit(final int unitParty) {
        this.power = getMaxPower();
        this.fuel = getMaxDistance();
        this.party = unitParty;
        if (this.party == 1) {
            this.dir = DIRECTION.W;
        } else {
            this.dir = DIRECTION.E;
        }
    }

    public final void setDir(final DIRECTION newDir) {
        this.dir = newDir;
    }

    public final void hasMoved(final GROUND ground) {
        this.fuel -= getMovePoints(ground);
    }

    public final int getOffense() {
        this.ableToAttack = false;
        this.fuel = 0;

        return getAttack();
    }

    public final int decreasePower(final int pow) {
        this.power = this.power - pow;
        return this.power;
    }

    public final void reset() {
        this.ableToAttack = true;
        this.fuel = getMaxDistance();
    }

    public final boolean canAttack() {
        return this.ableToAttack;
    }

    public final int getParty() {
        return party;
    }

    public final int getFuelLeft() {
        return fuel;
    }

    public final int getPowerLeft() {
        return power;
    }

    @Override
    public abstract String toString();

    public abstract int getMovePoints(final GROUND underGround);

    public abstract int getAttack();

    public abstract int getMaxPower();

    public abstract int getMaxDistance();

    public abstract int getMaxFireRange();

    public abstract int getMinFireRange();

    public abstract UNITTYPE getUnitType();

    public final Element toXML() {
        final Element element = new Element(MAPATTRIBUTES.UNIT.toString());
        element.addAttribute(StringResources.TYPE, getUnitType().toString());
        element.addAttribute(StringResources.FUEL, Integer.toString(fuel));
        element.addAttribute(StringResources.PARTY, Integer.toString(party));
        return element;
    }

    public final AbstractUnit trueClone() {
        final AbstractUnit clonedUnit = UnitFactory.createUnit(getUnitType(), party);

        clonedUnit.ableToAttack = ableToAttack;
        clonedUnit.power = power;
        clonedUnit.fuel = fuel;
        clonedUnit.dir = dir;

        return clonedUnit;
    }

    public DIRECTION getDirection() {
        return dir;
    }
}
