package restwars.service.ship;

import restwars.service.planet.Planet;
import restwars.service.player.Player;
import restwars.service.resource.InsufficientResourcesException;
import restwars.service.resource.Resources;

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
}
