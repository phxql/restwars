package restwars.service.event;

public enum EventType {
    BUILDING_COMPLETED(0),
    RESEARCH_COMPLETED(1),
    SHIP_COMPLETED(2),
    FLIGHT_RETURNED(3);

    private final int id;

    EventType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static EventType fromId(int id) {
        for (EventType buildingType : EventType.values()) {
            if (buildingType.getId() == id) {
                return buildingType;
            }
        }

        throw new IllegalArgumentException("Unknown id: " + id);
    }
}
