package restwars.rest.api.ship;

import com.google.common.base.Preconditions;
import restwars.rest.util.Helper;
import restwars.service.ship.Flight;

import java.util.List;

public class FlightResponse {
    private final String destination;

    private final long startedInRound;

    private final long arrivalInRound;

    private final List<ShipResponse> ships;

    private final String type;

    private final String direction;

    public FlightResponse(String destination, long startedInRound, long arrivalInRound, List<ShipResponse> ships, String type, String direction) {
        this.destination = destination;
        this.startedInRound = startedInRound;
        this.arrivalInRound = arrivalInRound;
        this.ships = ships;
        this.type = type;
        this.direction = direction;
    }

    public String getDestination() {
        return destination;
    }

    public long getStartedInRound() {
        return startedInRound;
    }

    public long getArrivalInRound() {
        return arrivalInRound;
    }

    public List<ShipResponse> getShips() {
        return ships;
    }

    public String getType() {
        return type;
    }

    public String getDirection() {
        return direction;
    }

    public static FlightResponse fromFlight(Flight flight) {
        Preconditions.checkNotNull(flight, "flight");

        return new FlightResponse(
                flight.getDestination().toString(), flight.getStartedInRound(), flight.getArrivalInRound(),
                Helper.mapToList(flight.getShips().asList(), ShipResponse::fromShip), flight.getType().toString(),
                flight.getDirection().toString()
        );
    }
}
