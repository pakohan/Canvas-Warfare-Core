package gameengine.map.field;

import gameengine.enumm.GROUND;

public final class Forest extends AbstractField {

    public Forest(final int party) {
        super(party);
    }

    @Override
    public GROUND getGround() {
        return GROUND.FOREST;
    }

    @Override
    public String toString() {
        return "F";
    }
}
