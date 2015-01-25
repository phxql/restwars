package restwars.service.flight;

public enum FlightDirection {
    OUTWARD(0),
    RETURN(1);

    private final int id;

    FlightDirection(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static FlightDirection fromId(int id) {
        for (FlightDirection type : FlightDirection.values()) {
            if (type.getId() == id) {
                return type;
            }
        }

        throw new IllegalArgumentException("Unknown id: " + id);
    }

}
