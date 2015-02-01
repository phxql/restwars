package restwars.model.planet;

import com.google.common.base.Objects;
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


    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("planet", planet)
                .add("owner", owner)
                .toString();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlanetWithOwner that = (PlanetWithOwner) o;

        return Objects.equal(this.planet, that.planet) &&
                Objects.equal(this.owner, that.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(planet, owner);
    }
}
