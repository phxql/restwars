package restwars.service.building;

import com.google.common.base.Preconditions;

import java.util.UUID;

public class Building {
    private final UUID id;

    private final BuildingType type;

    private final int level;

    private final UUID planetId;

    public Building(UUID id, BuildingType type, int level, UUID planetId) {
        Preconditions.checkArgument(level > 0, "level must be > 0");

        this.id = Preconditions.checkNotNull(id, "id");
        this.type = Preconditions.checkNotNull(type, "type");
        this.level = level;
        this.planetId = Preconditions.checkNotNull(planetId, "planetId");
    }

    public UUID getPlanetId() {
        return planetId;
    }

    public UUID getId() {
        return id;
    }

    public BuildingType getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }
}
