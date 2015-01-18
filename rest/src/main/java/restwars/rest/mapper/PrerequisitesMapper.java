package restwars.rest.mapper;

import com.google.common.base.Preconditions;
import restwars.restapi.dto.building.BuildingResponse;
import restwars.restapi.dto.metadata.PrerequisitesResponse;
import restwars.restapi.dto.technology.TechnologyResponse;
import restwars.service.techtree.Prerequisites;

import java.util.stream.Collectors;

/**
 * Maps prerequisites to DTOs and vice versa.
 */
public final class PrerequisitesMapper {
    private PrerequisitesMapper() {
    }

    public static PrerequisitesResponse fromPrerequisites(Prerequisites prerequisites) {
        Preconditions.checkNotNull(prerequisites, "prerequisites");

        return new PrerequisitesResponse(
                prerequisites.getBuildings().stream().map(b -> new BuildingResponse(b.getType().toString(), b.getLevel())).collect(Collectors.toList()),
                prerequisites.getTechnologies().stream().map(t -> new TechnologyResponse(t.getType().toString(), t.getLevel())).collect(Collectors.toList())
        );
    }
}
