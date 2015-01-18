package restwars.service.building;

import restwars.service.ServiceException;

public class BuildingException extends ServiceException {
    public static enum Reason {
        PREREQUISITES_NOT_FULFILLED,
        INSUFFICIENT_RESOURCES,
        NOT_ENOUGH_BUILD_QUEUES,
        EXISTING_BUILDING_NOT_FOUND
    }

    private final Reason reason;

    public BuildingException(Reason reason) {
        this.reason = reason;
    }

    public Reason getReason() {
        return reason;
    }
}
