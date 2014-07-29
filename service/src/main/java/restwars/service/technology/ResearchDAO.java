package restwars.service.technology;

import java.util.List;
import java.util.UUID;

/**
 * DAO for researches.
 */
public interface ResearchDAO {
    /**
     * Inserts the given research.
     *
     * @param research Research.
     */
    void insert(Research research);

    /**
     * Finds all researches which are done in the given round.
     *
     * @param round Round.
     * @return Researches which are done in the given round.
     */
    List<Research> findWithDone(long round);

    /**
     * Deletes the given research.
     *
     * @param research Research to delete.
     */
    void delete(Research research);

    /**
     * Finds all researches with the given planet id.
     *
     * @param planetId Id of the planet.
     * @return Researches.
     */
    List<Research> findWithPlanetId(UUID planetId);
}
