package restwars.service.building;

public enum BuildingType {
    COMMAND_CENTER(0),
    SHIPYARD(1),
    CRYSTAL_MINE(2),
    GAS_REFINERY(3),
    SOLAR_PANELS(4),
    RESEARCH_CENTER(5),
    TELESCOPE(6);

    private final int id;

    BuildingType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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
