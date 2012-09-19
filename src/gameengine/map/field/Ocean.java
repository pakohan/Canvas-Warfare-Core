package gameengine.map.field;

import gameengine.enumm.GROUND;

public final class Ocean extends AbstractField {
    public static final int NUMBER_EDGES = 4;
    private transient int border;
    private transient boolean[] corner;

    public Ocean(final int party) {
        super(party);
    }

    public void initWater(final int waterBorders, final boolean[] waterCorner) {
        border = waterBorders;
        corner = waterCorner.clone();
    }

    @Override
    public GROUND getGround() {
        return GROUND.WATER;
    }

    @Override
    public String toString() {
        return "~";
    }

    public int getBorder() {
        return border;
    }

    public boolean[] getCorner() {
        return corner.clone();
    }
}
