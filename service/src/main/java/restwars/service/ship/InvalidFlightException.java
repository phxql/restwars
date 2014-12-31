package restwars.service.ship;

public class InvalidFlightException extends Exception {
    public enum Reason {
        NO_SHIPS,
        NO_COLONY_SHIP
    }

    private final Reason reason;

    public InvalidFlightException(Reason reason) {
        this.reason = reason;
    }

    public Reason getReason() {
        return reason;
    }
}
