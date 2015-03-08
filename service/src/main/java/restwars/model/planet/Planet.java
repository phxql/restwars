package restwars.model.planet;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import restwars.model.player.Player;
import restwars.model.resource.Resources;

import java.util.UUID;

public class Planet {
    private final UUID id;

    private final Location location;

    private final UUID ownerId;

    private final Resources resources;

    private final long colonizedInRound;

    public Planet(UUID id, Location location, UUID ownerId, Resources resources, long colonizedInRound) {
        Preconditions.checkNotNull(resources, "resources");
        Preconditions.checkArgument(resources.getCrystals() >= 0, "crystals must be >= 0");
        Preconditions.checkArgument(resources.getGas() >= 0, "gas must be >= 0");
        Preconditions.checkArgument(resources.getCrystals() >= 0, "energy must be >= 0");
        Preconditions.checkArgument(colonizedInRound > 0, "colonizedInRound must be > 0");

        this.colonizedInRound = colonizedInRound;
        this.resources = resources;
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

    public UUID getOwnerId() {
        return ownerId;
    }

    public Resources getResources() {
        return resources;
    }

    public Planet withResources(Resources resources) {
        return new Planet(id, location, ownerId, resources, colonizedInRound);
    }

    public boolean isOwnedFrom(Player player) {
        Preconditions.checkNotNull(player, "player");

        return ownerId.equals(player.getId());
    }

    public long getColonizedInRound() {
        return colonizedInRound;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("location", location)
                .add("ownerId", ownerId)
                .add("resources", resources)
                .add("colonizedInRound", colonizedInRound)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Planet that = (Planet) o;

        return Objects.equal(this.id, that.id) &&
                Objects.equal(this.location, that.location) &&
                Objects.equal(this.ownerId, that.ownerId) &&
                Objects.equal(this.resources, that.resources) &&
                Objects.equal(this.colonizedInRound, that.colonizedInRound);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, location, ownerId, resources, colonizedInRound);
    }
}
