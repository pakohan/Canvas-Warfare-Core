package gameengine;

import gameengine.map.Map;
import gameengine.opponents.KI;
import gameengine.opponents.MapWrapper;

import java.io.FileNotFoundException;

public final class Game {
    private final transient Map map;
    private final transient MapWrapper wrapper;

    public Game(final String folderPath) throws FileNotFoundException {
        final MapProperty mapProperty = new MapProperty(folderPath);
        wrapper = new MapWrapper();
        final Core core = new Core(mapProperty);
        core.setOpponent(wrapper, 1);
        core.setOpponent(new KI(), 2);
        map = core.getMap();
        core.startGame();
    }

    public MapWrapper getWrapper() {
        return wrapper;
    }

    public Map getMap() {
        return map;
    }
}
