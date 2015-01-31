package restwars.model.flight;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

import java.util.UUID;

/**
 * A detected flight.
 */
public class DetectedFlight {
    private final UUID flightId;

    private final UUID playerId;

    private final long approximatedFleetSize;

    public DetectedFlight(UUID flightId, UUID playerId, long approximatedFleetSize) {
        this.playerId = Preconditions.checkNotNull(playerId, "playerId");
        this.flightId = Preconditions.checkNotNull(flightId, "flightId");
        this.approximatedFleetSize = approximatedFleetSize;
    }

    public UUID getFlightId() {
        return flightId;
    }

    public long getApproximatedFleetSize() {
        return approximatedFleetSize;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("flightId", flightId)
                .add("playerId", playerId)
                .add("approximatedFleetSize", approximatedFleetSize)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DetectedFlight that = (DetectedFlight) o;

        return Objects.equal(this.flightId, that.flightId) &&
                Objects.equal(this.playerId, that.playerId) &&
                Objects.equal(this.approximatedFleetSize, that.approximatedFleetSize);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(flightId, playerId, approximatedFleetSize);
    }
}
