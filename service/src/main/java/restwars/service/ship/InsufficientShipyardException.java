package restwars.service.ship;

public class InsufficientShipyardException extends Exception {
    private final int levelRequired;

    public InsufficientShipyardException(int levelRequired) {
        this.levelRequired = levelRequired;
    }

    public int getLevelRequired() {
        return levelRequired;
    }
}
