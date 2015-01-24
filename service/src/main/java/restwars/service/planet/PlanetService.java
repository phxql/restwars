package restwars.service.planet;

import restwars.service.player.Player;

import java.util.List;
import java.util.Optional;

/**
 * Service to manage planets.
 */
public interface PlanetService {
    /**
     * Creates a new planet at the given location.
     *
     * @param owner Owner of the planet.
     * @return Created planet.
     */
    Planet createStartPlanet(Player owner) throws CreateStartPlanetException;

    /**
     * Finds all planets which are owned by the given player.
     *
     * @param owner Owner.
     * @return List of all planets.
     */
    List<Planet> findWithOwner(Player owner);

    /**
     * Finds the planet with the given location.
     *
     * @param location Location.
     * @return Planet if found.
     */
    Optional<Planet> findWithLocation(Location location);

    /**
     * Updates the given planet.
     *
     * @param planet Planet to update.
     */
    void update(Planet planet);

    /**
     * Finds all planets.
     *
     * @return All planets.
     */
    List<Planet> findAll();
}
