package restwars.rest.mapper;

import com.google.common.base.Preconditions;
import restwars.model.technology.Research;
import restwars.restapi.dto.technology.ResearchResponse;

/**
 * Maps research entities to DTOs and vice versa.
 */
public final class ResearchMapper {
    private ResearchMapper() {
    }

    public static ResearchResponse fromResearch(Research research) {
        Preconditions.checkNotNull(research, "research");

        return new ResearchResponse(
                research.getType().toString(), research.getLevel(), research.getStarted(), research.getDone(),
                ResourcesMapper.fromResources(research.getResearchCost())
        );
    }
}
