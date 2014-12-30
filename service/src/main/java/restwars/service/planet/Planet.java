package restwars.service.planet;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import restwars.service.player.Player;
import restwars.service.resource.Resources;

import java.util.Optional;
import java.util.UUID;

public class Planet {
    private final UUID id;

    private final Location location;

    // TODO: Remove the Optional<>, only colonized planets appear as an instance of Planet.
    private final Optional<UUID> ownerId;

    private final long crystals;

    private final long gas;

    private final long energy;

    public Planet(UUID id, Location location, Optional<UUID> ownerId, long crystals, long gas, long energy) {
        Preconditions.checkArgument(crystals >= 0, "crystals must be >= 0");
        Preconditions.checkArgument(gas >= 0, "gas must be >= 0");
        Preconditions.checkArgument(energy >= 0, "energy must be >= 0");

        this.crystals = crystals;
        this.gas = gas;
        this.energy = energy;
        this.id = Preconditions.checkNotNull(id, "id");
        this.location = Preconditions.checkNotNull(location, "location");
        this.ownerId = Preconditions.checkNotNull(ownerId, "ownerId");
    }

    public UUID getId() {
        return id;
    }

    public Location getLocation() {
        return location;
    }

    public Optional<UUID> getOwnerId() {
        return ownerId;
    }

    public long getCrystals() {
        return crystals;
    }

    public long getGas() {
        return gas;
    }

    public long getEnergy() {
        return energy;
    }

    public Planet withResources(long crystals, long gas, long energy) {
        return new Planet(id, location, ownerId, crystals, gas, energy);
    }

    public boolean isOwnedFrom(Player player) {
        Preconditions.checkNotNull(player, "player");

        if (ownerId.isPresent()) {
            return ownerId.get().equals(player.getId());
        }

        return false;
    }

    public boolean hasResources(Resources resources) {
        Preconditions.checkNotNull(resources, "resources");

        return crystals >= resources.getCrystals() && gas >= resources.getGas() && energy >= resources.getEnergy();
    }

    public Planet withOwnerId(Optional<UUID> ownerId) {
        Preconditions.checkNotNull(ownerId, "ownerId");

        return new Planet(id, location, ownerId, crystals, gas, energy);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("location", location)
                .add("ownerId", ownerId)
                .add("crystals", crystals)
                .add("gas", gas)
                .add("energy", energy)
                .toString();
    }
}
