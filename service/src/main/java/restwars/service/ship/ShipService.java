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
     * @param start       Start planet.
     * @param destination Destination planet.
     * @param ships       Ships.
     * @return Flight.
     */
    Flight sendShipsToPlanet(Planet start, Planet destination, List<Ship> ships);
}
