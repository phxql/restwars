package restwars.rest.mapper;

import com.google.common.base.Preconditions;
import restwars.model.building.ConstructionSite;
import restwars.restapi.dto.building.ConstructionSiteResponse;

/**
 * Maps construction site entities to DTOs and vice versa.
 */
public final class ConstructionSiteMapper {
    private ConstructionSiteMapper() {
    }

    public static ConstructionSiteResponse fromConstructionSite(ConstructionSite constructionSite) {
        Preconditions.checkNotNull(constructionSite, "constructionSite");

        return new ConstructionSiteResponse(
                constructionSite.getType().toString(), constructionSite.getLevel(), constructionSite.getStarted(),
                constructionSite.getDone(), ResourcesMapper.fromResources(constructionSite.getBuildCost())
        );
    }
}
