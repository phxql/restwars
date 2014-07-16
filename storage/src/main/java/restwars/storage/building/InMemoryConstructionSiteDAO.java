package restwars.storage.building;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import restwars.service.building.ConstructionSite;
import restwars.service.building.ConstructionSiteDAO;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class InMemoryConstructionSiteDAO implements ConstructionSiteDAO {
    private final Map<UUID, ConstructionSite> constructionSites = Maps.newHashMap();

    @Override
    public void insert(ConstructionSite constructionSite) {
        Preconditions.checkNotNull(constructionSite, "constructionSite");

        constructionSites.put(constructionSite.getId(), constructionSite);
    }

    @Override
    public List<ConstructionSite> findWithPlanetId(UUID planetId) {
        Preconditions.checkNotNull(planetId, "planetId");

        return constructionSites.values().stream().filter(c -> c.getPlanetId().equals(planetId)).collect(Collectors.toList());
    }

    @Override
    public List<ConstructionSite> findWithDone(long round) {
        return constructionSites.values().stream().filter(c -> c.getDone() == round).collect(Collectors.toList());
    }

    @Override
    public void delete(ConstructionSite constructionSite) {
        Preconditions.checkNotNull(constructionSite, "constructionSite");

        constructionSites.values().remove(constructionSite);
    }
}
