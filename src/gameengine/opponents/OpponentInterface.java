package gameengine.opponents;

import gameengine.action.ActionInterface;

public interface OpponentInterface {
    void dispatchAction(ActionInterface action);
}
