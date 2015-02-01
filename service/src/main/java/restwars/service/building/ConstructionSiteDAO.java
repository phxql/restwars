package restwars.service.building;

import restwars.model.building.ConstructionSite;

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

    /**
     * Finds all construction sites which are done at the given round.
     *
     * @param round Round.
     * @return Construction sites.
     */
    List<ConstructionSite> findWithDone(long round);

    /**
     * Deletes the given construction site.
     *
     * @param constructionSite Construction site to delete.
     */
    void delete(ConstructionSite constructionSite);
}
