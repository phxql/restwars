package restwars.service.resource;

import restwars.service.ServiceException;

public class InsufficientResourcesException extends ServiceException {
    private final Resources needed;
    private final Resources available;

    public InsufficientResourcesException(Resources needed, Resources available) {
        this.needed = needed;
        this.available = available;
    }

    public Resources getNeeded() {
        return needed;
    }

    public Resources getAvailable() {
        return available;
    }
}
