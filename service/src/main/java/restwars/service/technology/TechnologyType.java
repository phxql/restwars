package restwars.service.technology;

import com.google.common.base.Preconditions;
import restwars.service.techtree.Prerequisites;

public enum TechnologyType {
    CRYSTAL_MINE_EFFICIENCY(0, "Increases the output of the crystal mine", Prerequisites.NONE),
    GAS_REFINERY_EFFICIENCY(1, "Increases the output of the gas refinery", Prerequisites.NONE),
    SOLAR_PANELS_EFFICIENCY(2, "Increases the output of the solar panels", Prerequisites.NONE),
    BUILDING_BUILD_COST_REDUCTION(3, "Reduces the build cost of buildings", Prerequisites.NONE),
    BUILDING_BUILD_TIME_REDUCTION(4, "Reduces the build time of buildings", Prerequisites.NONE);

    private final int id;

    private final String description;

    private final Prerequisites prerequisites;

    TechnologyType(int id, String description, Prerequisites prerequisites) {
        this.id = id;
        this.description = Preconditions.checkNotNull(description, "description");
        this.prerequisites = Preconditions.checkNotNull(prerequisites, "prerequisites");
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

    public static TechnologyType fromId(int id) {
        for (TechnologyType type : TechnologyType.values()) {
            if (type.getId() == id) {
                return type;
            }
        }

        throw new IllegalArgumentException("Unknown id " + id);
    }
}
