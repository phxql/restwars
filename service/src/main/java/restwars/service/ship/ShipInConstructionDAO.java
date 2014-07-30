package restwars.service.ship;

import java.util.List;
import java.util.UUID;

/**
 * DAO for ships in construction.
 */
public interface ShipInConstructionDAO {
    /**
     * Inserts the given ship in construction.
     *
     * @param shipInConstruction Ship in construction to insert.
     */
    void insert(ShipInConstruction shipInConstruction);

    /**
     * Finds all ships in construction which are done in the given round.
     *
     * @param round Round.
     * @return All ships in construction which are done in the given round.
     */
    List<ShipInConstruction> findWithDone(long round);

    /**
     * Finds all ships in construction with the given planet id.
     *
     * @param planetId Id of the planet.
     * @return All ships in construction with the given planet id.
     */
    List<ShipInConstruction> findWithPlanetId(UUID planetId);

    /**
     * Deletes the given ship in construction.
     *
     * @param shipInConstruction Ship in construction to delete.
     */
    void delete(ShipInConstruction shipInConstruction);
}
