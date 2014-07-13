package restwars.service.planet;

import com.google.common.base.Preconditions;

import java.util.Optional;
import java.util.UUID;

public class Planet {
    private final UUID id;

    private final Location location;

    private final Optional<UUID> ownerId;

    public Planet(UUID id, Location location, Optional<UUID> ownerId) {
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
}
