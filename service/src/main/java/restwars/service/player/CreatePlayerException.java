package restwars.service.player;

import restwars.service.ServiceException;

public class CreatePlayerException extends ServiceException {
    public static enum Reason {
        DUPLICATE_USERNAME,
        INVALID_USERNAME
    }

    private final Reason reason;

    public CreatePlayerException(Reason reason) {
        this.reason = reason;
    }

    public Reason getReason() {
        return reason;
    }
}
