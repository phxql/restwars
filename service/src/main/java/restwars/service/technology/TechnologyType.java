package restwars.service.technology;

import com.google.common.base.Preconditions;

public enum TechnologyType {
    CRYSTAL_MINE_EFFICIENCY(0, "Increases the output of the crystal mine"),
    GAS_REFINERY_EFFICIENCY(1, "Increases the output of the gas refinery"),
    SOLAR_PANELS_EFFICIENCY(2, "Increases the output of the solar panels"),
    BUILDING_BUILD_COST_REDUCTION(3, "Reduces the build cost of buildings"),
    BUILDING_BUILD_TIME_REDUCTION(4, "Reduces the build time of buildings");

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
