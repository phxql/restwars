package restwars.service.building;

public enum BuildingType {
    COMMAND_CENTER(0, "Constructs buildings"),
    SHIPYARD(1, "Constructs ships"),
    CRYSTAL_MINE(2, "Gathers crystals"),
    GAS_REFINERY(3, "Gathers gas"),
    SOLAR_PANELS(4, "Gathers energy"),
    RESEARCH_CENTER(5, "Researches technology"),
    TELESCOPE(6, "Scans the area for other planets");

    private final int id;

    private final String description;

    BuildingType(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
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
