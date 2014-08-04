package restwars.service.ship;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import restwars.service.planet.Location;

import java.util.UUID;

public class Flight {
    private final UUID id;

    private final UUID playerId;

    private final Location start;

    private final Location destination;

    private final long startedInRound;

    private final long arrivalInRound;

    private final Ships ships;

    private final long energyNeeded;

    private final FlightType type;

    private final FlightDirection direction;

    public Flight(UUID id, Location start, Location destination, long startedInRound, long arrivalInRound, Ships ships, long energyNeeded, FlightType type, UUID playerId, FlightDirection direction) {
        this.start = Preconditions.checkNotNull(start, "start");
        this.destination = Preconditions.checkNotNull(destination, "destination");
        Preconditions.checkArgument(startedInRound > 0, "startedInRound must be > 0");
        Preconditions.checkArgument(arrivalInRound > 0, "arrivalInRound must be > 0");
        Preconditions.checkArgument(energyNeeded > 0, "energyNeeded must be > 0");

        this.id = Preconditions.checkNotNull(id, "id");
        this.startedInRound = startedInRound;
        this.arrivalInRound = arrivalInRound;
        this.ships = Preconditions.checkNotNull(ships, "ships");
        this.energyNeeded = energyNeeded;
        this.type = Preconditions.checkNotNull(type, "type");
        this.playerId = Preconditions.checkNotNull(playerId, "playerId");
        this.direction = Preconditions.checkNotNull(direction, "direction");
    }

    public UUID getId() {
        return id;
    }

    public long getStartedInRound() {
        return startedInRound;
    }

    public long getArrivalInRound() {
        return arrivalInRound;
    }

    public Ships getShips() {
        return ships;
    }

    public long getEnergyNeeded() {
        return energyNeeded;
    }

    public FlightType getType() {
        return type;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public FlightDirection getDirection() {
        return direction;
    }

    public Location getStart() {
        return start;
    }

    public Location getDestination() {
        return destination;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("playerId", playerId)
                .add("start", start)
                .add("destination", destination)
                .add("startedInRound", startedInRound)
                .add("arrivalInRound", arrivalInRound)
                .add("ships", ships)
                .add("energyNeeded", energyNeeded)
                .add("type", type)
                .add("direction", direction)
                .toString();
    }
}
