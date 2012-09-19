package gameengine.action;

import gameengine.map.Map;
import gameengine.sound.SoundServer;
import gameengine.util.Logger;
import gameengine.util.Point;
import gameengine.util.VerifyInstallation;

public final class Attack implements ActionInterface {
    private static final long SLEEPTIME = 80;

    private final transient Point attackingUnit, attackedUnit;
    private final transient int party;

    public Attack(final Point attacker, final Point target, final int partyParam) {
        super();
        attackingUnit = attacker;
        attackedUnit = target;
        this.party = partyParam;
    }

    public String serialize() {
        return "ATTACK " + attackingUnit + " " + attackedUnit;
    }

    public boolean runAction(final Map map) {

        final SoundServer sound = VerifyInstallation.getSoundServer();
        sound.startAttackSound(map.getUnit(attackingUnit).getUnitType());

        try {
            Thread.sleep(SLEEPTIME);
        } catch (final InterruptedException e) {
            Logger.debug("Attack.run()", e.getMessage());
        }

        map.attackUnit(attackingUnit, attackedUnit);

        return true;
    }

    public int getParty() {
        return this.party;
    }
}
