package restwars.service.flight;

import restwars.model.flight.Flight;
import restwars.model.flight.FlightType;
import restwars.model.planet.Location;
import restwars.model.planet.Planet;
import restwars.model.player.Player;
import restwars.model.resource.Resources;
import restwars.model.ship.Ships;

import java.util.List;

/**
 * Service for flights.
 */
public interface FlightService {
    /**
     * Sends the given ships from the given start planet to the given destination planet.
     *
     * @param player      Player.
     * @param start       Start planet.
     * @param destination Destination planet.
     * @param ships       Ships.
     * @param flightType  Flight type.
     * @param cargo       Cargo. Only available for transport and colonize flights.
     * @return Flight.
     */
    Flight sendShipsToPlanet(Player player, Planet start, Location destination, Ships ships, FlightType flightType, Resources cargo) throws FlightException;

    /**
     * Finds all flights for a given player.
     *
     * @param player Player.
     * @return Player.
     */
    List<Flight> findFlightsForPlayer(Player player);

    /**
     * Find all flights which were started from a given planet.
     *
     * @param planet Planet.
     * @return Flights.
     */
    List<Flight> findFlightsStartedFromPlanet(Planet planet);

    /**
     * Finishes all flights which are done in the current round.
     */
    void finishFlights();
}
