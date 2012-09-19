package gameengine.opponents;

import gameengine.map.Map;

public interface AbstractOpponent {
    void startTurn(int playerID);

    void init(Map gameMap, OpponentInterface gameCore);

    void setReady(boolean b);
}
