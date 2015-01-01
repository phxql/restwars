package restwars.service.technology;

import restwars.service.ServiceException;

public class ResearchFailedException extends ServiceException {
    public static enum Reason {
        ALREADY_RUNNING,
        NOT_ENOUGH_RESEARCH_QUEUES,
        NO_RESEARCH_CENTER,
        INSUFFICIENT_RESOURCES
    }

    private final Reason reason;

    public ResearchFailedException(Reason reason) {
        this.reason = reason;
    }

    public Reason getReason() {
        return reason;
    }
}
