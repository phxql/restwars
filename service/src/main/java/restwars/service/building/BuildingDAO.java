package restwars.service.building;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * DAO for buildings.
 */
public interface BuildingDAO {
    /**
     * Returns the buildings on the given planet.
     *
     * @param planetId Id of the planet.
     * @return Buildings.
     */
    List<Building> findWithPlanetId(UUID planetId);

    /**
     * Inserts the given building.
     *
     * @param building Building.
     */
    void insert(Building building);

    /**
     * Updates the given building.
     *
     * @param building Building.
     */
    void update(Building building);

    /**
     * Returns the building on the given planet with the given type.
     *
     * @param planetId Id of the planet.
     * @param type     Building type.
     * @return Building.
     */
    Optional<Building> findWithPlanetIdAndType(UUID planetId, BuildingType type);
}
