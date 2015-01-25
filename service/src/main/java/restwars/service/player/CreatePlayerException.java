package restwars.service.player;

import restwars.service.ServiceException;

public class CreatePlayerException extends ServiceException {
    public static enum Reason {
        DUPLICATE_USERNAME,
        INVALID_USERNAME,
        UNIVERSE_FULL
    }

    private final Reason reason;

    public CreatePlayerException(Reason reason) {
        this.reason = reason;
    }

    public CreatePlayerException(Reason reason, Throwable cause) {
        super(cause);
        this.reason = reason;
    }

    public Reason getReason() {
        return reason;
    }
}
