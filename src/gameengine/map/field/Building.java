package gameengine.map.field;

import gameengine.enumm.GROUND;

public final class Building extends AbstractField {

    Building(final int party) {
        super(party);
    }

    @Override
    public GROUND getGround() {
        return GROUND.BUILDING;
    }

    @Override
    public String toString() {
        return "^";
    }
}
