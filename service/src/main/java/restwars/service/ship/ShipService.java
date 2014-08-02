package restwars.service.ship;

import restwars.service.planet.Planet;
import restwars.service.player.Player;
import restwars.service.resource.InsufficientResourcesException;
import restwars.service.resource.Resources;

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
     * Calculates the build time for the given type of ship.
     *
     * @param type Type of ship.
     * @return Build time in rounds.
     */
    long calculateBuildTime(ShipType type);

    /**
     * Calculates the build cost for the given ship type.
     *
     * @param type Type of ship.
     * @return Build cost.
     */
    Resources calculateBuildCost(ShipType type);

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
    List<Ship> findShipsOnPlanet(Planet planet);

    /**
     * Sends the given ships from the given start planet to the given destination planet.
     *
     * @param player      Player.
     * @param start       Start planet.
     * @param destination Destination planet.
     * @param ships       Ships.
     * @param flightType  Flight type.
     * @return Flight.
     */
    Flight sendShipsToPlanet(Player player, Planet start, Planet destination, List<Ship> ships, FlightType flightType);

    /**
     * Finds all flights for a given player.
     *
     * @param player Player.
     * @return Player.
     */
    List<Flight> findFlightsForPlayer(Player player);

    /**
     * Finishes all flights which are done in the current round.
     */
    void finishFlights();
}
