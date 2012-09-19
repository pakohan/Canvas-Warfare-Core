package gameengine.map;

import gameengine.enumm.DIRECTION;
import gameengine.enumm.GROUND;
import gameengine.enumm.UNITTYPE;
import gameengine.map.field.AbstractField;
import gameengine.map.field.Ocean;
import gameengine.map.unit.AbstractUnit;
import gameengine.map.unit.UnitFactory;
import gameengine.util.Element;
import gameengine.util.MapImageInterface;
import gameengine.util.Matrix;
import gameengine.util.Point;
import gameengine.util.ReallyCloneable;
import gameengine.util.StringResources;
import gameengine.util.StringResources.MAPATTRIBUTES;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

public final class Map extends Observable implements ReallyCloneable<Map>, MapImageInterface {
    private final transient Matrix<AbstractField> field;

    public Map(final Matrix<AbstractField> readyMap) {
        super();
        field = readyMap;
    }

    public void endRound() {
        for (int i = 0; i < field.getSize(); i++) {
            final AbstractUnit unit = field.getElement(i).getUnit();
            if (unit != null) {
                unit.reset();
            }
        }
    }

    public GROUND getGround(final Point coordinate) {
        final AbstractField area = field.getElement(coordinate);

        if (area == null) {
            return null;
        } else {
            return area.getGround();
        }
    }

    public int getBorder(final Point coordinate) {
        final AbstractField area = field.getElement(coordinate);
        if (area instanceof Ocean) {
            final Ocean ocean = (Ocean) area;
            return ocean.getBorder();
        } else {
            return 0;
        }
    }

    public int getFieldParty(final Point coordinate) {
        return field.getElement(coordinate).getParty();
    }

    public boolean[] getCorner(final Point coordinate) {
        final AbstractField area = field.getElement(coordinate);

        if (area instanceof Ocean) {
            final Ocean ocean = (Ocean) area;
            return ocean.getCorner();
        } else {
            return null;
        }
    }

    public AbstractUnit getUnit(final Point coordinate) {
        return field.getElement(coordinate).getUnit();
    }

    public boolean hasUnit(final Point coordinate) {
        return field.getElement(coordinate).getUnit() != null;
    }

    public boolean attackUnit(final Point coordAttacker, final Point target) {
        final AbstractUnit attacker = field.getElement(coordAttacker).getUnit();

        final boolean canAttack = attacker.canAttack();

        if (canAttack) {
            final AbstractField targetField = field.getElement(target);

            final AbstractUnit targetUnit = targetField.getUnit();
            if (targetUnit.decreasePower(attacker.getOffense()) < 1) {
                targetField.setUnit(null);
            }

            updateObserver(target);
        }

        return canAttack;
    }

    public Point moveUnit(final Point coordinateStart, final DIRECTION dir) {
        final Point target = coordinateStart.transform(dir);

        final AbstractField startField = field.getElement(coordinateStart);
        final AbstractUnit unit = startField.getUnit();
        unit.setDir(dir);
        unit.hasMoved(field.getElement(target).getGround());
        startField.setUnit(null);
        field.getElement(target).setUnit(unit);

        updateObserver(coordinateStart, target);

        return target;
    }

    private void updateObserver(final Point... coordinates) {
        final List<Point> points = new LinkedList<Point>();
        for (final Point point : coordinates) {
            points.add(point);
        }

        setChanged();
        notifyObservers(points);
    }

    public int getMovePoints(final Point coordinate, final GROUND ground) {
        return field.getElement(coordinate).getUnit().getMovePoints(ground);
    }

    public Element toXML() {
        final Element element = new Element(MAPATTRIBUTES.MAP.toString());
        final Point size = field.getDimension();
        final int xSize = size.getX();
        final int ySize = size.getY();
        element.addAttribute(StringResources.XSIZE, Integer.toString(xSize));
        element.addAttribute(StringResources.YSIZE, Integer.toString(ySize));

        for (int i = 1; i <= ySize; i++) {
            final Element row = new Element(MAPATTRIBUTES.ROW.toString());
            for (int j = 1; j <= xSize; j++) {
                row.addChild(field.getElement(new Point(j, i)).toXML());
            }
            element.addChild(row);
        }

        return element;
    }

    public boolean conquer(final Point coordinate, final int party) {
        final AbstractField buildingArea = field.getElement(coordinate);
        final AbstractUnit unit = buildingArea.getUnit();
        if (buildingArea.getGround() == GROUND.BUILDING && unit != null && unit.canAttack() && unit.getParty() == party) {
            buildingArea.setParty(party);
            updateObserver(coordinate);
            unit.getOffense();
            return true;
        } else {
            return false;
        }
    }

    public boolean createUnit(final Point coordinate, final UNITTYPE type, final int party) {
        final AbstractField newField = field.getElement(coordinate);

        if (newField == null || newField.getGround() != GROUND.BUILDING || newField.getUnit() != null) {
            return false;
        }

        final AbstractUnit unit = UnitFactory.createUnit(type, party);
        unit.getOffense();
        newField.setUnit(unit);
        updateObserver(coordinate);

        return true;
    }

    public Point getDimension() {
        return field.getDimension();
    }

    public List<Point> getUnits(final int party) {
        final List<Point> list = new LinkedList<Point>();

        final Point dimension = field.getDimension();

        for (int i = 1; i <= dimension.getY(); i++) {
            for (int j = 1; j <= dimension.getX(); j++) {
                final Point location = new Point(j, i);
                if (hasUnit(location) && getUnit(location).getParty() == party) {
                    list.add(location);
                }
            }
        }

        return list;
    }

    public Map trueClone() {
        final int size = field.getSize();
        final List<AbstractField> clonedList = new ArrayList<AbstractField>(size);

        for (int i = 0; i < size; i++) {
            clonedList.add(field.getElement(i).trueClone());
        }

        final Map map = new Map(new Matrix<AbstractField>(field.getDimension(), clonedList));

        map.deleteObservers();

        return map;
    }
}
