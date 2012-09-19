package gameengine.map;

import gameengine.enumm.DIRECTION;
import gameengine.enumm.GROUND;
import gameengine.enumm.UNITTYPE;
import gameengine.map.field.AbstractField;
import gameengine.map.field.FieldFactory;
import gameengine.map.field.Ocean;
import gameengine.map.unit.UnitFactory;
import gameengine.util.Logger;
import gameengine.util.Matrix;
import gameengine.util.Point;
import gameengine.util.StringResources;
import gameengine.util.StringResources.MAPATTRIBUTES;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public final class MapFactory extends DefaultHandler {

    private final transient List<AbstractField> area;
    private final transient Map map;
    private transient int xSize, ySize;

    private MapFactory(final File file) {
        super();
        area = new LinkedList<AbstractField>();

        xSize = 0;
        ySize = 0;

        try {
            SAXParserFactory.newInstance().newSAXParser().parse(file, this);
        } catch (final SAXException e) {
            Logger.debug("Attack.Map()", e.getMessage());
        } catch (final ParserConfigurationException e) {
            Logger.debug("Attack.Map()", e.getMessage());
        } catch (final IOException e) {
            Logger.debug("Attack.Map()", e.getMessage());
        }

        if (xSize > 0 && ySize > 0) {
            final Matrix<AbstractField> fields = new Matrix<AbstractField>(new Point(xSize, ySize), area);
            initWater(fields);
            map = new Map(fields);
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void startElement(final String uri, final String localName, final String qName, final Attributes attributes) throws SAXException {
        switch (MAPATTRIBUTES.valueOf(qName.toUpperCase(Locale.getDefault()))) {
        case MAP:
            xSize = Integer.parseInt(attributes.getValue(StringResources.XSIZE));
            ySize = Integer.parseInt(attributes.getValue(StringResources.YSIZE));
            break;
        case ROW:
            break;
        case FIELD:
            final GROUND underGround = GROUND.valueOf(attributes.getValue(StringResources.TYPE));
            int party;
            if (underGround == GROUND.BUILDING) {
                party = Integer.parseInt(attributes.getValue(StringResources.PARTY));
            } else {
                party = 0;
            }

            final AbstractField field = FieldFactory.createField(underGround, party);
            setField(field);
            break;
        case UNIT:
            final UNITTYPE type = UNITTYPE.valueOf(attributes.getValue(StringResources.TYPE).toUpperCase());
            area.get(area.size() - 1).setUnit(UnitFactory.createUnit(type, Integer.parseInt(attributes.getValue(StringResources.PARTY))));
            break;
        default:
        }
    }

    private boolean setField(final AbstractField field) {
        return area.add(field);
    }

    public static Map readMap(final File file) {
        return new MapFactory(file).map;
    }

    public static void saveMap(final Map map, final FileOutputStream mapSaveFile) {
        final PrintWriter out = new PrintWriter(mapSaveFile);
        out.println(map.toXML().toString());
        out.close();
    }

    private static void initWater(final Matrix<AbstractField> fields) {
        final Point coordinates = fields.getDimension();

        for (int i = 1; i <= coordinates.getX(); i++) {
            for (int j = 1; j <= coordinates.getY(); j++) {
                final Point waterLocation = new Point(i, j);
                final AbstractField scannedField = fields.getElement(waterLocation);
                if (scannedField instanceof Ocean) {
                    final Ocean waterField = (Ocean) scannedField;
                    final int[] border = initBanks(waterLocation, fields);
                    final boolean[] corner = initCorners(border, waterLocation, fields);
                    final int waterBorders = getNumberFromInt(border);
                    waterField.initWater(waterBorders, corner);
                }
            }
        }
    }

    private static int getNumberFromInt(final int[] border) {
        int number = 0;
        for (int i = 0; i < border.length; i++) {
            number = number << 1;
            number = number | border[i];
        }

        return number;
    }

    private static boolean[] initCorners(final int[] borders, final Point coordinate, final Matrix<AbstractField> fields) {
        final boolean[] corners = {false, false, false, false};

        int index = 0;

        initOneCorner(borders, coordinate.transform(DIRECTION.N).transform(DIRECTION.E), index++, fields);
        initOneCorner(borders, coordinate.transform(DIRECTION.S).transform(DIRECTION.E), index++, fields);
        initOneCorner(borders, coordinate.transform(DIRECTION.S).transform(DIRECTION.W), index++, fields);
        initOneCorner(borders, coordinate.transform(DIRECTION.N).transform(DIRECTION.W), index, fields);

        return corners;
    }

    private static boolean initOneCorner(final int[] borders, final Point coordinate, final int index, final Matrix<AbstractField> fields) {
        boolean returnVal = false;

        if (borders[index] == 0 && borders[(index + 1) % Ocean.NUMBER_EDGES] == 0) {
            final AbstractField waterField = fields.getElement(coordinate);
            returnVal = waterField != null && waterField.getGround() != GROUND.WATER;
        }

        return returnVal;
    }

    private static int[] initBanks(final Point coordinate, final Matrix<AbstractField> field) {
        final AbstractField[] fields = new AbstractField[Ocean.NUMBER_EDGES];
        final int[] borders = {0, 0, 0, 0};
        int index = 0;

        fields[index++] = field.getElement(coordinate.transform(DIRECTION.N));
        fields[index++] = field.getElement(coordinate.transform(DIRECTION.E));
        fields[index++] = field.getElement(coordinate.transform(DIRECTION.S));
        fields[index] = field.getElement(coordinate.transform(DIRECTION.W));

        for (int i = 0; i < fields.length; i++) {
            if (fields[i] == null) {
                borders[i] = 0;
            } else {
                final GROUND ground = fields[i].getGround();
                if (ground == GROUND.WATER) {
                    borders[i] = 0;
                } else {
                    borders[i] = 1;
                }
            }
        }

        return borders;
    }
}
