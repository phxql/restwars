package restwars.service.building;

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
}
