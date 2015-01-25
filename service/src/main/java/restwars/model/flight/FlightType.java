package restwars.model.flight;

import com.google.common.base.Preconditions;

public enum FlightType {
    ATTACK(0, "Attack an enemy planet"),
    COLONIZE(1, "Colonize an empty planet"),
    TRANSPORT(2, "Transport resources to a friendly planet"),
    TRANSFER(3, "Transfer ships to a friendly planet");

    private final int id;

    private final String description;

    FlightType(int id, String description) {
        this.id = id;
        this.description = Preconditions.checkNotNull(description, "description");
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public static FlightType fromId(int id) {
        for (FlightType type : FlightType.values()) {
            if (type.getId() == id) {
                return type;
            }
        }

        throw new IllegalArgumentException("Unknown id: " + id);
    }
}
