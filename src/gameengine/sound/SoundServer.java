package gameengine.sound;

import java.util.Map;

import gameengine.enumm.UNITTYPE;

public final class SoundServer {
    private final transient Map<UNITTYPE, Sound> moves;
    private final transient Map<UNITTYPE, Sound> attacks;

    public SoundServer(final Map<UNITTYPE, Sound> moveSounds, final Map<UNITTYPE, Sound> attackSounds) {
        moves = moveSounds;
        attacks = attackSounds;
    }

    public void startMoveSound(final UNITTYPE type) {
        final Sound sound = moves.get(type);
        sound.startLoop();
    }

    public void stopMoveSound(final UNITTYPE type) {
        final Sound sound = moves.get(type);
        sound.stop();
    }

    public void startAttackSound(final UNITTYPE type) {
        final Sound sound = attacks.get(type);
        sound.start();
    }
}
