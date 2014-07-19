package restwars.service.technology;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * DAO for technologies.
 */
public interface TechnologyDAO {
    List<Technology> findAllWithPlayerId(UUID playerId);

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
