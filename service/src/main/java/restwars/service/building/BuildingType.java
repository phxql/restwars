package restwars.service.building;

import com.google.common.base.Preconditions;
import restwars.service.techtree.Prerequisites;

public enum BuildingType {
    COMMAND_CENTER(0, "Constructs buildings", Prerequisites.NONE),
    CRYSTAL_MINE(2, "Gathers crystals", Prerequisites.building(BuildingType.COMMAND_CENTER, 1)),
    GAS_REFINERY(3, "Gathers gas", Prerequisites.building(BuildingType.COMMAND_CENTER, 1)),
    SOLAR_PANELS(4, "Gathers energy", Prerequisites.building(BuildingType.COMMAND_CENTER, 1)),
    RESEARCH_CENTER(5, "Researches technology", Prerequisites.building(BuildingType.COMMAND_CENTER, 1)),
    TELESCOPE(6, "Scans the area for other planets", Prerequisites.building(BuildingType.COMMAND_CENTER, 1)),
    SHIPYARD(1, "Constructs ships", Prerequisites.buildings(new Prerequisites.Building(BuildingType.COMMAND_CENTER, 1), new Prerequisites.Building(BuildingType.RESEARCH_CENTER, 1), new Prerequisites.Building(BuildingType.TELESCOPE, 1)));

    private final int id;

    private final String description;

    private final Prerequisites prerequisites;

    BuildingType(int id, String description, Prerequisites prerequisites) {
        this.id = id;
        this.prerequisites = Preconditions.checkNotNull(prerequisites, "prerequisites");
        this.description = Preconditions.checkNotNull(description, "description");
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Prerequisites getPrerequisites() {
        return prerequisites;
    }

    public static BuildingType fromId(int id) {
        for (BuildingType buildingType : BuildingType.values()) {
            if (buildingType.getId() == id) {
                return buildingType;
            }
        }

        throw new IllegalArgumentException("Unknown id: " + id);
    }
}
