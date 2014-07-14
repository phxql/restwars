package restwars.service.planet;

import restwars.service.player.Player;

import java.util.List;

/**
 * Service to manage planets.
 */
public interface PlanetService {
    /**
     * Creates a new planet at the given location.
     *
     * @param owner    Owner of the planet.
     * @return Created planet.
     */
    Planet createStartPlanet(Player owner);

    /**
     * Finds all planets which are owned by the given player.
     *
     * @param owner Owner.
     * @return List of all planets.
     */
    List<Planet> findWithOwner(Player owner);
}
