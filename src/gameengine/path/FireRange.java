package gameengine.path;

import gameengine.map.Map;
import gameengine.map.unit.AbstractUnit;
import gameengine.util.Matrix;
import gameengine.util.Point;
import gameengine.util.Range;

import java.util.LinkedList;
import java.util.List;

public final class FireRange implements Range {

    private final transient Map map;
    private final transient Point unitLocation;
    private final transient int unitParty;

    private final transient Matrix<Boolean> sectors;
    private final transient int minFireRange;
    private final transient int maxFireRange;
    private final List<Point> attackableFields;

    public FireRange(final Point location, final AbstractUnit attribute, final Map mapParam) {
        map = mapParam;
        unitLocation = location;
        minFireRange = attribute.getMinFireRange();
        maxFireRange = attribute.getMaxFireRange();
        unitParty = attribute.getParty();
        attackableFields = new LinkedList<Point>();

        sectors = new Matrix<Boolean>(map.getDimension());
        sectors.fill();

        initBooleanField();
    }

    private void initBooleanField() {
        final Point dimension = map.getDimension();

        for (int i = 1; i <= dimension.getY(); i++) {
            for (int j = 1; j <= dimension.getX(); j++) {

                final long dis = getDistance(j, i, unitLocation);
                boolean isReachable = dis >= minFireRange;
                isReachable = isReachable && dis <= maxFireRange;

                final Point location = new Point(j, i);
                final AbstractUnit unit = map.getUnit(location);
                if (unit != null && unit.getParty() != unitParty && isReachable) {
                    attackableFields.add(location);
                }

                sectors.setElement(location, isReachable);
            }
        }
    }

    public static long getDistance(final int xCoordinate, final int yCoordinate, final Point coordinate) {
        final int xDistance = xCoordinate - coordinate.getX();
        final int yDistance = yCoordinate - coordinate.getY();

        return (int) Math.round(Math.sqrt(xDistance * xDistance + yDistance * yDistance));
    }

    public boolean isReachable(final Point location) {
        return sectors.getElement(location);
    }

    public int getAttackableUnits() {
        return attackableFields.size();
    }

    public Point getAttackbleField() {
        int power = Integer.MAX_VALUE;
        Point ret = null;
        for (final Point point : attackableFields) {
            final AbstractUnit attribute = map.getUnit(point);
            if (power > attribute.getPowerLeft()) {
                ret = point;
                power = attribute.getPowerLeft();
            }
        }

        return ret;
    }
}
