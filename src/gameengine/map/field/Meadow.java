package gameengine.map.field;

import gameengine.enumm.GROUND;

public final class Meadow extends AbstractField {

    public Meadow(final int party) {
        super(party);
    }

    @Override
    public GROUND getGround() {
        return GROUND.GRASS;
    }

    @Override
    public String toString() {
        return " ";
    }
}
