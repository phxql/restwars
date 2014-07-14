package restwars.storage.building;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import restwars.service.building.ConstructionSite;
import restwars.service.building.ConstructionSiteDAO;

import java.util.Map;
import java.util.UUID;

public class InMemoryConstructionSiteDAO implements ConstructionSiteDAO {
    private final Map<UUID, ConstructionSite> constructionSites = Maps.newHashMap();

    @Override
    public void insert(ConstructionSite constructionSite) {
        Preconditions.checkNotNull(constructionSite, "constructionSite");

        constructionSites.put(constructionSite.getId(), constructionSite);
    }
}
