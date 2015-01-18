package restwars.service.ship;

import restwars.service.ServiceException;

public class BuildShipException extends ServiceException {
    public static enum Reason {
        NO_SHIPYARD,
        NOT_ENOUGH_BUILD_QUEUES,
        INSUFFICIENT_RESOURCES,
        PREREQUISITES_NOT_FULFILLED
    }

    private final Reason reason;

    public BuildShipException(Reason reason) {
        this.reason = reason;
    }

    public Reason getReason() {
        return reason;
    }
}
