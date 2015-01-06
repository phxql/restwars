package restwars.service.event;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

import java.util.UUID;

public class Event {
    private final UUID id;

    private final UUID playerId;

    private final UUID planetId;

    private final EventType type;

    private final long round;

    public Event(UUID id, UUID playerId, UUID planetId, EventType type, long round) {
        this.id = Preconditions.checkNotNull(id, "id");
        this.playerId = Preconditions.checkNotNull(playerId, "playerId");
        this.planetId = Preconditions.checkNotNull(planetId, "planetId");
        this.type = Preconditions.checkNotNull(type, "type");
        this.round = round;
    }

    public UUID getId() {
        return id;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public UUID getPlanetId() {
        return planetId;
    }

    public EventType getType() {
        return type;
    }

    public long getRound() {
        return round;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("playerId", playerId)
                .add("planetId", planetId)
                .add("type", type)
                .add("round", round)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event that = (Event) o;

        return Objects.equal(this.id, that.id) &&
                Objects.equal(this.playerId, that.playerId) &&
                Objects.equal(this.planetId, that.planetId) &&
                Objects.equal(this.type, that.type) &&
                Objects.equal(this.round, that.round);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, playerId, planetId, type, round);
    }
}
