package restwars.service.planet;

import com.google.common.base.Preconditions;

import java.util.Optional;
import java.util.UUID;

public class Planet {
    private final UUID id;

    private final Location location;

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
}
