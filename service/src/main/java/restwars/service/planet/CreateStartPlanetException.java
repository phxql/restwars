package restwars.service.planet;

import restwars.service.ServiceException;

public class CreateStartPlanetException extends ServiceException {
    public static enum Reason {
        UNIVERSE_FULL
    }

    private final Reason reason;

    public CreateStartPlanetException(Reason reason) {
        this.reason = reason;
    }

    public Reason getReason() {
        return reason;
    }
}
