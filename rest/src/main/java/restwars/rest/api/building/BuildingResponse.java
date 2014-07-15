package restwars.rest.api.building;

import com.google.common.base.Preconditions;
import restwars.service.building.Building;

public class BuildingResponse {
    private final String type;

    private final int level;

    public BuildingResponse(String type, int level) {
        this.type = type;
        this.level = level;
    }

    public String getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }

    public static BuildingResponse fromBuilding(Building building) {
        Preconditions.checkNotNull(building, "building");

        return new BuildingResponse(building.getType().toString(), building.getLevel());
    }
}
