package restwars.service.telescope;

import restwars.service.planet.Planet;
import restwars.service.player.Player;
import restwars.service.ship.DetectedFlightWithSender;

import java.util.List;

public interface TelescopeService {
    /**
     * Finds all planets in vicinity of the given planet. The range depends on the level of the telescope on the planet.
     *
     * @param planet Planet where the telescope is located.
     * @return Planets in vicinity.
     */
    List<PlanetWithOwner> scan(Planet planet) throws ScanException;

    /**
     * Detects incoming flights for the given player.
     *
     * @param player Player.
     * @return All incoming flights.
     */
    List<DetectedFlightWithSender> findDetectedFlights(Player player);
}
