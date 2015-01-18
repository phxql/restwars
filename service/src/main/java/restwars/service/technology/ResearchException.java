package restwars.service.technology;

import restwars.service.ServiceException;

public class ResearchException extends ServiceException {
    public static enum Reason {
        ALREADY_RUNNING,
        NOT_ENOUGH_RESEARCH_QUEUES,
        NO_RESEARCH_CENTER,
        INSUFFICIENT_RESOURCES,
        PREREQUISITES_NOT_FULFILLED
    }

    private final Reason reason;

    public ResearchException(Reason reason) {
        this.reason = reason;
    }

    public Reason getReason() {
        return reason;
    }
}
