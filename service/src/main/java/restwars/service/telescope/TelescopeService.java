package restwars.service.telescope;

import restwars.service.flight.DetectedFlightWithSender;
import restwars.service.planet.Planet;
import restwars.service.planet.PlanetWithOwner;
import restwars.service.player.Player;

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

    /**
     * Detect flights.
     */
    void detectFlights();

    /**
     * Calculates the flight detection range for the given telescope level.
     *
     * @param telescopeLevel Telescope level.
     * @return Flight detection range.
     */
    int calculateFlightDetectionRange(int telescopeLevel);

    /**
     * Calculates the fleet detection variance for the given telescope level.
     *
     * @param telescopeLevel Telescope level.
     * @return Fleet detection variance.
     */
    double calculateFleetSizeVariance(int telescopeLevel);
}
