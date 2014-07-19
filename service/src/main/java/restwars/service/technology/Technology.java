package restwars.service.technology;

import com.google.common.base.Preconditions;

import java.util.UUID;

public class Technology {
    private final UUID id;

    private final TechnologyType type;

    private final int level;

    private final UUID playerId;

    public Technology(UUID id, TechnologyType type, int level, UUID playerId) {
        Preconditions.checkArgument(level > 0, "level must be > 0");

        this.id = Preconditions.checkNotNull(id, "id");
        this.type = Preconditions.checkNotNull(type, "type");
        this.level = level;
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

    public UUID getPlayerId() {
        return playerId;
    }
}
