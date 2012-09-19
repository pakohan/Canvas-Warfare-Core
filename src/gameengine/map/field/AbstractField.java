package gameengine.map.field;

import gameengine.enumm.GROUND;
import gameengine.map.unit.AbstractUnit;
import gameengine.util.Element;
import gameengine.util.ReallyCloneable;
import gameengine.util.StringResources;
import gameengine.util.StringResources.MAPATTRIBUTES;

/**
 * The class Field represents a single square of the map. This means the Map is
 * made out of Fields. It can contain Units.
 */
public abstract class AbstractField implements ReallyCloneable<AbstractField> {

    private AbstractUnit unit;
    private int party;

    public AbstractField(final int buildingParty) {
        super();
        this.party = buildingParty;
    }

    public final void setUnit(final AbstractUnit unitOfField) {
        this.unit = unitOfField;
    }

    public final AbstractUnit getUnit() {
        return this.unit;
    }

    public abstract GROUND getGround();

    public final AbstractField trueClone() {
        final AbstractField field = FieldFactory.createField(getGround(), party);
        if (unit != null) {
            field.unit = unit.trueClone();
        }
        return field;
    }

    @Override
    public abstract String toString();

    public final int getParty() {
        return this.party;
    }

    public final void setParty(final int newParty) {
        this.party = newParty;
    }

    public final Element toXML() {
        final Element fieldElement = new Element(MAPATTRIBUTES.FIELD.toString());
        fieldElement.addAttribute(StringResources.TYPE, getGround().toString());
        if (getGround() == GROUND.BUILDING) {
            fieldElement.addAttribute(StringResources.PARTY, Integer.toString(party));
        }

        if (unit != null) {
            fieldElement.addChild(unit.toXML());
        }

        return fieldElement;
    }
}
