package restwars.service.planet;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * DAO for planets.
 */
public interface PlanetDAO {
    /**
     * Inserts the given planet.
     *
     * @param planet Planet.
     */
    void insert(Planet planet);

    /**
     * Finds all planets which are owned by the given player.
     *
     * @param ownerId Id of the owner.
     * @return List of all planets.
     */
    List<Planet> findWithOwnerId(UUID ownerId);

    /**
     * Finds the planet with the given location.
     *
     * @param location Location.
     * @return Planet if found.
     */
    Optional<Planet> findWithLocation(Location location);
}
