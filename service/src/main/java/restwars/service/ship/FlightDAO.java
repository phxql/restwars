package restwars.service.ship;

import restwars.service.planet.Location;

import java.util.List;
import java.util.UUID;

public interface FlightDAO {
    void insert(Flight flight);

    List<Flight> findWithPlayerId(UUID playerId);

    List<Flight> findWithArrival(long arrival);

    void update(Flight flight);

    void delete(Flight flight);

    List<Flight> findWithStart(Location location);
}
