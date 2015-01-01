package restwars.service.ship;

import restwars.service.ServiceException;

public class FlightException extends ServiceException {
    public static enum Reason {
        NO_SHIPS,
        NO_COLONY_SHIP,
        NO_CARGO_ALLOWED,
        NOT_ENOUGH_CARGO_SPACE,
        INSUFFICIENT_FUEL,
        NOT_ENOUGH_SHIPS_ON_PLANET,
        INSUFFICIENT_RESOURCES
    }

    private final Reason reason;

    public FlightException(Reason reason) {
        this.reason = reason;
    }

    public Reason getReason() {
        return reason;
    }
}
