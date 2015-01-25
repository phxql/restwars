package restwars.service.flight;

import restwars.service.ServiceException;

public class FlightException extends ServiceException {
    public static enum Reason {
        INVALID_DESTINATION,
        SAME_START_AND_DESTINATION,
        NO_SHIPS,
        NO_COLONY_SHIP,
        NO_CARGO_ALLOWED,
        NOT_ENOUGH_CARGO_SPACE,
        INSUFFICIENT_FUEL,
        NOT_ENOUGH_SHIPS_ON_PLANET,
        INSUFFICIENT_RESOURCES,
        CANT_CARGO_ENERGY
    }

    private final Reason reason;

    public FlightException(Reason reason) {
        this.reason = reason;
    }

    public Reason getReason() {
        return reason;
    }
}
