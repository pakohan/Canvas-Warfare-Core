package gameengine.util;

import gameengine.enumm.GROUND;
import gameengine.map.unit.AbstractUnit;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractImageProvider<T> {
    private static final int EIGHT = 8;

    private static final int ROW_GROUND = 5;
    private static final int ROW_WATER = 7;
    private static final int ROW_MISC = 6;
    private static final int ROW_EXPLOSION = 9;

    private static final int COLUMN_FIRERANGE = 8;
    private static final int COLUMN_SELECTION = 7;
    private static final int COLUMN_CURSOR = 6;
    private static final int COLUMN_FIRSTC = 5;

    public final T getFieldImage(final MapImageInterface map, final Point coordinate) {
        T image;
        final GROUND ground = map.getGround(coordinate);
        switch (ground) {
        case WATER:
            image = getWater(map.getBorder(coordinate), map.getCorner(coordinate));
            break;
        case BUILDING:
            image = getBuilding(map.getFieldParty(coordinate));
            break;
        case FOREST:
        case MOUNTAIN:
        case GRASS:
            image = getImageFromResource(ground.ordinal() + 1, ROW_GROUND);
            break;
        default:
            image = null;
        }

        if (map.hasUnit(coordinate)) {
            final List<T> images = new LinkedList<T>();
            images.add(image);
            images.add(getUnit(map.getUnit(coordinate)));
            image = combineBitmaps(images);
        }

        return image;
    }

    public final T getCursor() {
        return getImageFromResource(COLUMN_CURSOR, ROW_MISC);
    }

    public final T getSelection() {
        return getImageFromResource(COLUMN_SELECTION, ROW_MISC);
    }

    public final T getFirerange() {
        return getImageFromResource(COLUMN_FIRERANGE, ROW_MISC);
    }

    private T getBuilding(final int party) {
        return getImageFromResource(party + 1, ROW_MISC);
    }

    public final T getExplosion(final int explosionStep) {
        return getImageFromResource(explosionStep, ROW_EXPLOSION);
    }

    private T getWater(final int border, final boolean[] corners) {
        final List<T> waterfield = new LinkedList<T>();
        waterfield.add(getImageFromResource((border % EIGHT) + 1, ROW_WATER + (border / EIGHT)));

        for (int i = 0; i < corners.length; i++) {
            if (corners[i]) {
                waterfield.add(getImageFromResource(COLUMN_FIRSTC + i, ROW_GROUND));
            }
        }

        return combineBitmaps(waterfield);
    }

    private T getUnit(final AbstractUnit unit) {
        return getUnitImage(unit);
    }

    public abstract T getUnitImage(AbstractUnit unit);

    public final int getNumberExplosionsteps() {
        return EIGHT;
    }

    public abstract int getResolution();

    public abstract int getGapBetweenAreas();

    public abstract int getResolutionGap();

    public abstract T combineBitmaps(final List<T> bitmaps);

    public abstract T getImageFromResource(final int xGrid, final int yGrid);
}
