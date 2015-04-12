package restwars.model.points;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

import java.util.UUID;

/**
 * Points.
 */
public class Points {
    /**
     * Id.
     */
    private final UUID id;

    /**
     * Player id.
     */
    private final UUID playerId;

    /**
     * Round in which the points where calculated.
     */
    private final long round;

    /**
     * Points.
     */
    private final long points;

    /**
     * Constructor.
     *
     * @param id       Id.
     * @param playerId Player id.
     * @param round    Round in which the points where calculated.
     * @param points   Points.
     */
    public Points(UUID id, UUID playerId, long round, long points) {
        Preconditions.checkArgument(round > 0, "round must be > 0");
        Preconditions.checkArgument(points > 0, "points must be > 0");

        this.id = Preconditions.checkNotNull(id, "id");
        this.playerId = Preconditions.checkNotNull(playerId, "playerId");
        this.round = round;
        this.points = points;
    }

    public UUID getId() {
        return id;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public long getRound() {
        return round;
    }

    public long getPoints() {
        return points;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Points that = (Points) o;

        return Objects.equal(this.id, that.id) &&
                Objects.equal(this.playerId, that.playerId) &&
                Objects.equal(this.round, that.round) &&
                Objects.equal(this.points, that.points);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, playerId, round, points);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("playerId", playerId)
                .add("round", round)
                .add("points", points)
                .toString();
    }
}
