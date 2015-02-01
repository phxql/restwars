package restwars.service.ship;

import restwars.model.planet.Planet;
import restwars.model.player.Player;
import restwars.model.ship.ShipInConstruction;
import restwars.model.ship.ShipType;
import restwars.model.ship.Ships;

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
    ShipInConstruction buildShip(Player player, Planet planet, ShipType type) throws BuildShipException;

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
     * Manifests the given ships on the given planet.
     *
     * @param player Player.
     * @param planet Planet.
     * @param ships  Ships.
     */
    void manifestShips(Player player, Planet planet, Ships ships);
}
