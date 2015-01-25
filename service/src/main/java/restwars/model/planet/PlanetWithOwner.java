package restwars.model.planet;

import com.google.common.base.Preconditions;
import restwars.model.player.Player;

public class PlanetWithOwner {
    private final Planet planet;

    private final Player owner;

    public PlanetWithOwner(Planet planet, Player owner) {
        this.planet = Preconditions.checkNotNull(planet, "planet");
        this.owner = Preconditions.checkNotNull(owner, "owner");
    }

    public Planet getPlanet() {
        return planet;
    }

    public Player getOwner() {
        return owner;
    }
}
