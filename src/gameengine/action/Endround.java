package gameengine.action;

import gameengine.map.Map;

public final class Endround implements ActionInterface {
    private final transient int party;

    public Endround(final int playerID) {
        this.party = playerID;
    }

    public String serialize() {
        return "ENDROUND";
    }

    public boolean runAction(final Map map) {
        map.endRound();
        return true;
    }

    public int getParty() {
        return this.party;
    }
}
