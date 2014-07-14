package restwars.service.building;

import com.google.common.base.Preconditions;

import java.util.UUID;

public class ConstructionSite {
    private final UUID id;

    private final BuildingType type;

    private final int level;

    private final UUID planetId;

    private final long started;

    private final long done;

    public ConstructionSite(UUID id, BuildingType type, int level, UUID planetId, long started, long done) {
        Preconditions.checkArgument(level > 0, "level must be > 0");
        Preconditions.checkArgument(started > 0, "started must be > 0");
        Preconditions.checkArgument(done > 0, "done must be > 0");
        Preconditions.checkArgument(done >= started, "done must be >= started");

        this.id = Preconditions.checkNotNull(id, "id");
        this.type = Preconditions.checkNotNull(type, "type");
        this.level = level;
        this.planetId = Preconditions.checkNotNull(planetId, "planetId");
        this.started = started;
        this.done = done;
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

    public UUID getPlanetId() {
        return planetId;
    }

    public long getStarted() {
        return started;
    }

    public long getDone() {
        return done;
    }
}
