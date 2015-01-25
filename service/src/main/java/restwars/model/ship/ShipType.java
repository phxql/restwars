package restwars.model.ship;

import com.google.common.base.Preconditions;

public enum ShipType {
    MOSQUITO(0, "Small and cheap fighter ship"),
    COLONY(1, "Colonizes planets"),
    PROBE(2, "Fast and cheap scout"),
    MULE(3, "Freighter"),
    DAGGER(4, "Very fast fighter ship"),
    DAEDALUS(5, "Sophisticated fighter ship");

    private final int id;

    private final String description;

    ShipType(int id, String description) {
        this.id = id;
        this.description = Preconditions.checkNotNull(description, "description");
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public static ShipType fromId(int id) {
        for (ShipType type : ShipType.values()) {
            if (type.getId() == id) {
                return type;
            }
        }

        throw new IllegalArgumentException("Unknown id: " + id);
    }
}
