package restwars.model.technology;

import com.google.common.base.Preconditions;

public enum TechnologyType {
    BUILDING_BUILD_COST_REDUCTION(0, "Reduces the build cost of buildings"),
    COMBUSTION_ENGINE(1, "Reduces the flight cost for ships with combustion engines");

    private final int id;

    private final String description;

    TechnologyType(int id, String description) {
        this.id = id;
        this.description = Preconditions.checkNotNull(description, "description");
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
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
