package restwars.service.technology;

public enum TechnologyType {
    CRYSTAL_MINE_EFFICIENCY(0),
    GAS_REFINERY_EFFICIENCY(1),
    SOLAR_PANELS_EFFICIENCY(2),
    BUILDING_BUILD_COST_REDUCTION(3),
    BUILDING_BUILD_TIME_REDUCTION(4);

    private final int id;

    TechnologyType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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
