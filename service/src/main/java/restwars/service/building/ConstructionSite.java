package restwars.service.building;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import restwars.model.building.BuildingType;

import java.util.UUID;

public class ConstructionSite {
    private final UUID id;

    private final BuildingType type;

    private final int level;

    private final UUID planetId;

    private final UUID playerId;

    private final long started;

    private final long done;

    public ConstructionSite(UUID id, BuildingType type, int level, UUID planetId, UUID playerId, long started, long done) {
        Preconditions.checkArgument(level > 0, "level must be > 0");
        Preconditions.checkArgument(started > 0, "started must be > 0");
        Preconditions.checkArgument(done > 0, "done must be > 0");
        Preconditions.checkArgument(done >= started, "done must be >= started");

        this.id = Preconditions.checkNotNull(id, "id");
        this.type = Preconditions.checkNotNull(type, "type");
        this.playerId = Preconditions.checkNotNull(playerId, "playerId");
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

    public UUID getPlayerId() {
        return playerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConstructionSite that = (ConstructionSite) o;

        return Objects.equal(this.id, that.id) &&
                Objects.equal(this.type, that.type) &&
                Objects.equal(this.level, that.level) &&
                Objects.equal(this.planetId, that.planetId) &&
                Objects.equal(this.playerId, that.playerId) &&
                Objects.equal(this.started, that.started) &&
                Objects.equal(this.done, that.done);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, type, level, planetId, playerId, started,
                done);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("type", type)
                .add("level", level)
                .add("planetId", planetId)
                .add("playerId", playerId)
                .add("started", started)
                .add("done", done)
                .toString();
    }
}
