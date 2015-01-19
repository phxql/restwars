package restwars.service.ship;

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
}
