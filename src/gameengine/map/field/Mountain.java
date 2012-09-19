package gameengine.map.field;

import gameengine.enumm.GROUND;

public final class Mountain extends AbstractField {
    public Mountain(final int party) {
        super(party);
    }

    @Override
    public GROUND getGround() {
        return GROUND.MOUNTAIN;
    }

    @Override
    public String toString() {
        return "W";
    }
}
