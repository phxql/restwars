package restwars.service.technology;

import restwars.service.ServiceException;

public class InsufficientResearchCenterException extends ServiceException {
    private final int levelRequired;

    public InsufficientResearchCenterException(int levelRequired) {
        this.levelRequired = levelRequired;
    }

    public int getLevelRequired() {
        return levelRequired;
    }
}
