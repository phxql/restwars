package restwars.service.ship;

public enum FlightType {
    ATTACK(0),
    COLONIZE(1),
    TRANSPORT(2);

    private final int id;

    FlightType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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
