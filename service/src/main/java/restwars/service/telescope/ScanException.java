package restwars.service.telescope;

import restwars.service.ServiceException;

public class ScanException extends ServiceException {
    public static enum Reason {
        NO_TELESCOPE
    }

    private final Reason reason;

    public ScanException(Reason reason) {
        this.reason = reason;
    }

    public Reason getReason() {
        return reason;
    }
}
