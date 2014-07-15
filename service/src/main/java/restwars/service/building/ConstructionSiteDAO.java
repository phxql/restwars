package restwars.service.building;

import java.util.List;
import java.util.UUID;

/**
 * DAO for construction sites.
 */
public interface ConstructionSiteDAO {
    /**
     * Inserts the given construction site.
     *
     * @param constructionSite Construction site.
     */
    void insert(ConstructionSite constructionSite);

    /**
     * Returns the construction sites on the given planet.
     *
     * @param planetId Id of the planet.
     * @return Construction sites.
     */
    List<ConstructionSite> findWithPlanetId(UUID planetId);
}
