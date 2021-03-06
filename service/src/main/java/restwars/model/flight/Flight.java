package restwars.model.flight;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import restwars.model.planet.Location;
import restwars.model.resource.Resources;
import restwars.model.ship.Ships;

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

    private final Resources cargo;

    private final double speed;

    private final boolean detected;

    public Flight(UUID id, Location start, Location destination, long startedInRound, long arrivalInRound, Ships ships, long energyNeeded, FlightType type, UUID playerId, FlightDirection direction, Resources cargo, double speed, boolean detected) {
        Preconditions.checkArgument(startedInRound > 0, "startedInRound must be > 0");
        Preconditions.checkArgument(arrivalInRound > 0, "arrivalInRound must be > 0");
        Preconditions.checkArgument(energyNeeded > 0, "energyNeeded must be > 0");
        Preconditions.checkArgument(speed > 0, "speed must be > 0");

        this.cargo = Preconditions.checkNotNull(cargo, "cargo");
        this.start = Preconditions.checkNotNull(start, "start");
        this.destination = Preconditions.checkNotNull(destination, "destination");
        this.id = Preconditions.checkNotNull(id, "id");
        this.startedInRound = startedInRound;
        this.arrivalInRound = arrivalInRound;
        this.ships = Preconditions.checkNotNull(ships, "ships");
        this.energyNeeded = energyNeeded;
        this.type = Preconditions.checkNotNull(type, "type");
        this.playerId = Preconditions.checkNotNull(playerId, "playerId");
        this.direction = Preconditions.checkNotNull(direction, "direction");
        this.detected = detected;
        this.speed = speed;
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

    public Resources getCargo() {
        return cargo;
    }

    public boolean isDetected() {
        return detected;
    }

    public double getSpeed() {
        return speed;
    }

    public Flight withCargo(Resources cargo) {
        return new Flight(id, start, destination, startedInRound, arrivalInRound, ships, energyNeeded, type, playerId, direction, cargo, speed, detected);
    }

    public Flight withShips(Ships ships) {
        return new Flight(id, start, destination, startedInRound, arrivalInRound, ships, energyNeeded, type, playerId, direction, cargo, speed, detected);
    }

    public Flight withDetected(boolean detected) {
        return new Flight(id, start, destination, startedInRound, arrivalInRound, ships, energyNeeded, type, playerId, direction, cargo, speed, detected);
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
                .add("cargo", cargo)
                .add("speed", speed)
                .add("detected", detected)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Flight that = (Flight) o;

        return Objects.equal(this.id, that.id) &&
                Objects.equal(this.playerId, that.playerId) &&
                Objects.equal(this.start, that.start) &&
                Objects.equal(this.destination, that.destination) &&
                Objects.equal(this.startedInRound, that.startedInRound) &&
                Objects.equal(this.arrivalInRound, that.arrivalInRound) &&
                Objects.equal(this.ships, that.ships) &&
                Objects.equal(this.energyNeeded, that.energyNeeded) &&
                Objects.equal(this.type, that.type) &&
                Objects.equal(this.direction, that.direction) &&
                Objects.equal(this.cargo, that.cargo) &&
                Objects.equal(this.speed, that.speed) &&
                Objects.equal(this.detected, that.detected);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, playerId, start, destination, startedInRound, arrivalInRound,
                ships, energyNeeded, type, direction, cargo,
                speed, detected);
    }
}
