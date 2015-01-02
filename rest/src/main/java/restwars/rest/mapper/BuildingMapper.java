package restwars.rest.mapper;

import com.google.common.base.Preconditions;
import restwars.restapi.dto.building.BuildingResponse;
import restwars.service.building.Building;

public final class BuildingMapper {
    private BuildingMapper() {
    }

    public static BuildingResponse fromBuilding(Building building) {
        Preconditions.checkNotNull(building, "building");

        return new BuildingResponse(building.getType().toString(), building.getLevel());
    }
}
