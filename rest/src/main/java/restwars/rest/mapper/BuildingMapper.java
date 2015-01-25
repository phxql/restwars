package restwars.rest.mapper;

import com.google.common.base.Preconditions;
import restwars.model.building.Building;
import restwars.restapi.dto.building.BuildingResponse;

/**
 * Maps building entities to DTOs and vice versa.
 */
public final class BuildingMapper {
    private BuildingMapper() {
    }

    public static BuildingResponse fromBuilding(Building building) {
        Preconditions.checkNotNull(building, "building");

        return new BuildingResponse(building.getType().toString(), building.getLevel());
    }
}
