package restwars.rest.mapper;

import com.google.common.base.Preconditions;
import restwars.restapi.dto.building.ConstructionSiteResponse;
import restwars.service.building.ConstructionSite;

public final class ConstructionSiteMapper {
    private ConstructionSiteMapper() {
    }

    public static ConstructionSiteResponse fromConstructionSite(ConstructionSite constructionSite) {
        Preconditions.checkNotNull(constructionSite, "constructionSite");

        return new ConstructionSiteResponse(constructionSite.getType().toString(), constructionSite.getLevel(), constructionSite.getStarted(), constructionSite.getDone());
    }
}
