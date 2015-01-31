package restwars.model.technology;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

import java.util.UUID;

public class Research {
    private final UUID id;

    private final TechnologyType type;

    private final int level;

    private final long started;

    private final long done;

    private final UUID planetId;

    private final UUID playerId;

    public Research(UUID id, TechnologyType type, int level, long started, long done, UUID planetId, UUID playerId) {
        Preconditions.checkArgument(level > 0, "level must be > 0");
        Preconditions.checkArgument(started > 0, "started must be > 0");
        Preconditions.checkArgument(done > 0, "done must be > 0");

        this.id = Preconditions.checkNotNull(id, "id");
        this.type = Preconditions.checkNotNull(type, "type");
        this.level = level;
        this.started = started;
        this.done = done;
        this.planetId = Preconditions.checkNotNull(planetId, "planetId");
        this.playerId = Preconditions.checkNotNull(playerId, "playerId");
    }

    public UUID getId() {
        return id;
    }

    public TechnologyType getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }

    public long getStarted() {
        return started;
    }

    public long getDone() {
        return done;
    }

    public UUID getPlanetId() {
        return planetId;
    }

    public UUID getPlayerId() {
        return playerId;
    }


    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("type", type)
                .add("level", level)
                .add("started", started)
                .add("done", done)
                .add("planetId", planetId)
                .add("playerId", playerId)
                .toString();
    }
}
