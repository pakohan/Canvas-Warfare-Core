package gameengine.util;

import gameengine.enumm.GROUND;
import gameengine.map.unit.AbstractUnit;

public interface MapImageInterface {

    GROUND getGround(Point coordinate);

    boolean[] getCorner(Point coordinate);

    int getBorder(Point coordinate);

    int getFieldParty(Point coordinate);

    AbstractUnit getUnit(Point coordinate);

    boolean hasUnit(Point coordinate);

}
