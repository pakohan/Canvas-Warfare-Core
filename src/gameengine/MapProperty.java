package gameengine;

import gameengine.map.Map;
import gameengine.map.MapFactory;
import gameengine.util.Logger;
import gameengine.util.VerifyInstallation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public final class MapProperty extends DefaultHandler {
    enum MAPATTRIBUTES {
        MAPPROPERTY, STAR, NAME, DESCRIPTION, SIZE, PLAYER
    }

    private transient String name;
    private transient String star;
    private transient String description;
    private final transient String path;
    private String opponent;
    private transient int xSize;
    private transient int ySize;
    private transient int numberPlayers;
    private transient Map map;

    private transient String tmpString;

    public MapProperty(final String folderPath) {
        super();
        this.path = folderPath;

        try {
            SAXParserFactory.newInstance().newSAXParser().parse(VerifyInstallation.openFile(VerifyInstallation.MAP_DIR, path, VerifyInstallation.DESCRIPTION_FILE), this);
        } catch (final SAXException e) {
            Logger.debug("MapProperty.MapProperty()", e.getMessage());
        } catch (final ParserConfigurationException e) {
            Logger.debug("MapProperty.MapProperty()", e.getMessage());
        } catch (final IOException e) {
            Logger.debug("MapProperty.MapProperty()", e.getMessage());
        }
    }

    public String getName() {
        return this.name;
    }

    public int getxSize() {
        return this.xSize;
    }

    public int getySize() {
        return this.ySize;
    }

    public String getStar() {
        return this.star;
    }

    public String getDescription() {
        return this.description;
    }

    public int getNumberPlayers() {
        return this.numberPlayers;
    }

    public String getPath() {
        return this.path;
    }

    public String getOpponent() {
        return this.opponent;
    }

    public void setOpponent(final String opponentParam) {
        this.opponent = opponentParam;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public Map getMap() throws FileNotFoundException {
        if (map == null) {
            map = MapFactory.readMap(VerifyInstallation.openFile(VerifyInstallation.MAP_DIR, path, VerifyInstallation.MAP_FILE));
        }

        return map;
    }

    @Override
    public void characters(final char[] chars, final int start, final int length) throws SAXException {
        this.tmpString = new String(chars.clone(), start, length);
    }

    @Override
    public void startElement(final String uri, final String localName, final String qName, final Attributes attributes) throws SAXException {
        try {
            switch (MAPATTRIBUTES.valueOf(qName.toUpperCase(Locale.getDefault()))) {
            case STAR:
                this.star = attributes.getValue("star");
                break;
            case PLAYER:
                this.numberPlayers = Integer.valueOf(attributes.getValue("numberplayer"));
                break;
            case SIZE:
                this.xSize = Integer.valueOf(attributes.getValue("xsize"));
                this.ySize = Integer.valueOf(attributes.getValue("ysize"));
                break;
            default:
            }
        } catch (final IllegalArgumentException x) {
            Logger.debug("MapProperty.startElement()", "Unbekanntes Attribut: " + qName);
        }
    }

    @Override
    public void endElement(final String uri, final String localName, final String qName) throws SAXException {

        try {
            switch (MAPATTRIBUTES.valueOf(qName.toUpperCase(Locale.getDefault()))) {
            case NAME:
                this.name = this.tmpString;
                this.tmpString = "";
                break;
            case DESCRIPTION:
                this.description = this.tmpString;
                this.tmpString = "";
                break;
            default:
            }
        } catch (final IllegalArgumentException x) {
            Logger.debug("MapProperty.endElement()", "Unbekanntes Attribut: " + qName);
            return;
        }

    }
}
