package gameengine.map.field;

import gameengine.enumm.GROUND;

public final class FieldFactory {
    private FieldFactory() {
    }

    public static AbstractField createField(final GROUND type, final int party) {
        AbstractField field;

        switch (type) {
        case BUILDING:
            field = new Building(party);
            break;
        case FOREST:
            field = new Forest(party);
            break;
        case GRASS:
            field = new Meadow(party);
            break;
        case MOUNTAIN:
            field = new Mountain(party);
            break;
        case WATER:
            field = new Ocean(party);
            break;
        default:
            field = null;
        }

        return field;
    }
}
