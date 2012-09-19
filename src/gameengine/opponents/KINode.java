package gameengine.opponents;

import gameengine.enumm.DIRECTION;
import gameengine.util.Node;

public final class KINode extends Node {
    private final transient int rating;

    public KINode(final DIRECTION dir, final int steps, final int fieldRating) {
        super(dir, steps);

        rating = fieldRating;
    }

    public int getRating() {
        return rating;
    }
}
