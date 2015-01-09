package restwars.service.technology;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * DAO for technologies.
 */
public interface TechnologyDAO {
    /**
     * Finds all technologies for the given player.
     *
     * @param playerId Id of the player.
     * @return List of technologies.
     */
    List<Technology> findAllWithPlayerId(UUID playerId);

    /**
     * Finds the technology with the given type for the given player.
     *
     * @param playerId Id of the player.
     * @param type     Technology type.
     * @return Technology.
     */
    Optional<Technology> findWithPlayerId(UUID playerId, TechnologyType type);

    /**
     * Updates the given technology.
     *
     * @param technology Technology.
     */
    void update(Technology technology);

    /**
     * Inserts the given technology.
     *
     * @param technology Technology.
     */
    void insert(Technology technology);
}
