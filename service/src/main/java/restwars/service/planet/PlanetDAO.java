package restwars.service.planet;

import restwars.service.telescope.PlanetWithOwner;

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

    /**
     * Updates the given planet.
     *
     * @param planet Planet.
     */
    void update(Planet planet);

    /**
     * Finds all planets.
     *
     * @return All planets.
     */
    List<Planet> findAll();

    /**
     * Finds the planet with the given id.
     *
     * @param id Id.
     * @return Planet if found.
     */
    Optional<Planet> findWithId(UUID id);

    /**
     * Finds all planets in the given range.
     *
     * @param galaxyMin      Minimum galaxy value.
     * @param galaxyMax      Maximum galaxy value.
     * @param solarSystemMin Minimum solar system value.
     * @param solarSystemMax Maximum solar system value.
     * @param planetMin      Minimum planet value.
     * @param planetMax      Maximum planet value.
     * @return Planet with their owners.
     */
    List<PlanetWithOwner> findInRange(int galaxyMin, int galaxyMax, int solarSystemMin, int solarSystemMax, int planetMin, int planetMax);
}
