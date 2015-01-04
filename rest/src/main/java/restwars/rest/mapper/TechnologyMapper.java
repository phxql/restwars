package restwars.rest.mapper;

import com.google.common.base.Preconditions;
import restwars.restapi.dto.technology.TechnologyResponse;
import restwars.service.technology.Technology;

public final class TechnologyMapper {
    private TechnologyMapper() {
    }

    public static TechnologyResponse fromTechnology(Technology technology) {
        Preconditions.checkNotNull(technology, "technology");

        return new TechnologyResponse(technology.getType().toString(), technology.getLevel());
    }
}
