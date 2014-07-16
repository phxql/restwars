package restwars.service.building;

import restwars.service.ServiceException;

public class BuildingNotFoundException extends ServiceException {
    public BuildingNotFoundException() {
    }

    public BuildingNotFoundException(String message) {
        super(message);
    }
}
