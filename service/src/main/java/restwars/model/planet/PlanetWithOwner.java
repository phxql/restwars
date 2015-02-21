package restwars.model.planet;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import restwars.model.player.Player;

import java.util.Optional;

public class PlanetWithOwner {
    private final Location location;

    private final Optional<Planet> planet;

    private final Optional<Player> owner;

    public PlanetWithOwner(Location location, Optional<Planet> planet, Optional<Player> owner) {
        this.location = Preconditions.checkNotNull(location, "location");
        this.planet = Preconditions.checkNotNull(planet, "planet");
        this.owner = Preconditions.checkNotNull(owner, "owner");
    }

    public Location getLocation() {
        return location;
    }

    public Optional<Planet> getPlanet() {
        return planet;
    }

    public Optional<Player> getOwner() {
        return owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlanetWithOwner that = (PlanetWithOwner) o;

        return Objects.equal(this.location, that.location) &&
                Objects.equal(this.planet, that.planet) &&
                Objects.equal(this.owner, that.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(location, planet, owner);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("location", location)
                .add("planet", planet)
                .add("owner", owner)
                .toString();
    }
}
