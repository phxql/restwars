package restwars.service.building;

import java.util.List;
import java.util.UUID;

/**
 * DAO for buildings.
 */
public interface BuildingDAO {
    /**
     * Returns the buildings on the given planets.
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
}
