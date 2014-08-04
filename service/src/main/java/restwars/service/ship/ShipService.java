package restwars.service.ship;

import restwars.service.planet.Location;
import restwars.service.planet.Planet;
import restwars.service.player.Player;
import restwars.service.resource.InsufficientResourcesException;

import java.util.List;

public interface ShipService {
    /**
     * Builds a ship.
     *
     * @param player Player.
     * @param planet Planet to build the ship on.
     * @param type   Type of ship.
     * @return Ship in construction.
     */
    ShipInConstruction buildShip(Player player, Planet planet, ShipType type) throws InsufficientResourcesException;

    /**
     * Finds all ships in construction on the given planet.
     *
     * @param planet Planet.
     * @return Ships in construction.
     */
    List<ShipInConstruction> findShipsInConstructionOnPlanet(Planet planet);

    /**
     * Finishes all ships in construction which are done in the current round.
     */
    void finishShipsInConstruction();

    /**
     * Finds the ships on the given planet.
     *
     * @param planet Planet.
     * @return Hangar.
     */
    Ships findShipsOnPlanet(Planet planet);

    /**
     * Sends the given ships from the given start planet to the given destination planet.
     *
     * @param player      Player.
     * @param start       Start planet.
     * @param destination Destination planet.
     * @param ships       Ships.
     * @param flightType  Flight type.
     * @return Flight.
     * @throws NotEnoughShipsException If not enough ships are available on the start planet.
     */
    Flight sendShipsToPlanet(Player player, Planet start, Location destination, Ships ships, FlightType flightType) throws NotEnoughShipsException;

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

    /**
     * Manifests the given ships on the given planet.
     *
     * @param player Player.
     * @param planet Planet.
     * @param ships  Ships.
     */
    void manifestShips(Player player, Planet planet, Ships ships);
}
