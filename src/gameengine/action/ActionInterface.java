package gameengine.action;

import gameengine.map.Map;

public interface ActionInterface {
    String serialize();

    boolean runAction(final Map map);

    int getParty();
}
