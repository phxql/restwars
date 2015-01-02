package restwars.rest.mapper;

import com.google.common.base.Preconditions;
import restwars.restapi.dto.technology.ResearchResponse;
import restwars.service.technology.Research;

public final class ResearchMapper {
    private ResearchMapper() {
    }

    public static ResearchResponse fromResearch(Research research) {
        Preconditions.checkNotNull(research, "research");

        return new ResearchResponse(research.getType().toString(), research.getLevel(), research.getStarted(), research.getDone());
    }
}
