package restwars.service.technology;

import com.google.common.base.Preconditions;
import restwars.service.techtree.Prerequisites;

public enum TechnologyType {
    BUILDING_BUILD_COST_REDUCTION(3, "Reduces the build cost of buildings", Prerequisites.NONE);

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
