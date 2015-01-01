package restwars.service.ship;

import restwars.service.ServiceException;

public class InsufficientShipyardException extends ServiceException {
    private final int levelRequired;

    public InsufficientShipyardException(int levelRequired) {
        this.levelRequired = levelRequired;
    }

    public int getLevelRequired() {
        return levelRequired;
    }
}
