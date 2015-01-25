package restwars.service.fight;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import restwars.service.planet.Planet;
import restwars.service.player.Player;

public class FightWithPlanetAndPlayer {
    private final Fight fight;
    private final Planet planet;
    private final Player attacker;
    private final Player defender;

    public FightWithPlanetAndPlayer(Fight fight, Planet planet, Player attacker, Player defender) {
        this.fight = Preconditions.checkNotNull(fight, "fight");
        this.planet = Preconditions.checkNotNull(planet, "planet");
        this.attacker = Preconditions.checkNotNull(attacker, "attacker");
        this.defender = Preconditions.checkNotNull(defender, "defender");
    }

    public Fight getFight() {
        return fight;
    }

    public Planet getPlanet() {
        return planet;
    }

    public Player getAttacker() {
        return attacker;
    }

    public Player getDefender() {
        return defender;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("fight", fight)
                .add("planet", planet)
                .add("attacker", attacker)
                .add("defender", defender)
                .toString();
    }
}
