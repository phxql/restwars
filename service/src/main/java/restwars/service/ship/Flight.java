package restwars.service.ship;

import com.google.common.base.Preconditions;

import java.util.List;
import java.util.UUID;

public class Flight {
    private final UUID id;

    private final UUID playerId;

    private final UUID startPlanetId;

    private final UUID destinationPlanetId;

    private final long started;

    private final long arrives;

    private final List<Ship> ships;

    private final long energyNeeded;

    private final FlightType flightType;

    public Flight(UUID id, UUID startPlanetId, UUID destinationPlanetId, long started, long arrives, List<Ship> ships, long energyNeeded, FlightType flightType, UUID playerId) {
        Preconditions.checkArgument(started > 0, "started must be > 0");
        Preconditions.checkArgument(arrives > 0, "arrives must be > 0");
        Preconditions.checkArgument(energyNeeded > 0, "energyNeeded must be > 0");

        this.id = Preconditions.checkNotNull(id, "id");
        this.startPlanetId = Preconditions.checkNotNull(startPlanetId, "startPlanetId");
        this.destinationPlanetId = Preconditions.checkNotNull(destinationPlanetId, "destinationPlanetId");
        this.started = started;
        this.arrives = arrives;
        this.ships = Preconditions.checkNotNull(ships, "ships");
        this.energyNeeded = energyNeeded;
        this.flightType = Preconditions.checkNotNull(flightType, "flightType");
        this.playerId = Preconditions.checkNotNull(playerId, "playerId");
    }

    public UUID getId() {
        return id;
    }

    public UUID getStartPlanetId() {
        return startPlanetId;
    }

    public UUID getDestinationPlanetId() {
        return destinationPlanetId;
    }

    public long getStarted() {
        return started;
    }

    public long getArrives() {
        return arrives;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public long getEnergyNeeded() {
        return energyNeeded;
    }

    public FlightType getFlightType() {
        return flightType;
    }

    public UUID getPlayerId() {
        return playerId;
    }
}
