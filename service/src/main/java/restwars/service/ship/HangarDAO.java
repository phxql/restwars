package restwars.service.ship;

import java.util.Optional;
import java.util.UUID;

public interface HangarDAO {
    /**
     * Finds the hangar with the given planet id.
     *
     * @param planetId Planet id.
     * @return Hangar.
     */
    Optional<Hangar> findWithPlanetId(UUID planetId);

    /**
     * Updates the given hangar.
     *
     * @param hangar Hangar to update.
     */
    void update(Hangar hangar);

    /**
     * Inserts the given hangar.
     *
     * @param hangar Hangar to insert.
     */
    void insert(Hangar hangar);
}
