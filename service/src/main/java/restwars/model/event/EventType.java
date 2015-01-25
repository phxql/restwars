package restwars.model.event;

import com.google.common.base.Preconditions;

public enum EventType {
    BUILDING_COMPLETED(0, "A building has been constructed"),
    RESEARCH_COMPLETED(1, "A technology has been researched"),
    SHIP_COMPLETED(2, "A ship has been constructed"),
    FLIGHT_RETURNED(3, "A flight has returned"),
    PLANET_COLONIZED(4, "A new planet has been colonized"),
    TRANSPORT_ARRIVED(5, "A transport has arrived"),
    FIGHT_HAPPENED(6, "A fight has happened"),
    TRANSFER_ARRIVED(7, "A ship transfer has been finished"),
    FLIGHT_DETECTED(8, "An enemy flight has been detected"),
    FLIGHT_HAS_BEEN_DETECTED(9, "Your flight has been detected by the enemy");

    private final int id;

    private final String description;

    EventType(int id, String description) {
        this.id = id;
        this.description = Preconditions.checkNotNull(description, "description");
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
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
