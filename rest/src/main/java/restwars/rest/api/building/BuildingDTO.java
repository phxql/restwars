package restwars.rest.api.building;

import com.google.common.base.Preconditions;
import restwars.service.building.Building;

public class BuildingDTO {
    private final String type;

    private final int level;

    public BuildingDTO(String type, int level) {
        this.type = type;
        this.level = level;
    }

    public String getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }

    public static BuildingDTO fromBuilding(Building building) {
        Preconditions.checkNotNull(building, "building");

        return new BuildingDTO(building.getType().toString(), building.getLevel());
    }
}
