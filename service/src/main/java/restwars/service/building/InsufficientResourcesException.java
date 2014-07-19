package restwars.service.building;

import restwars.service.ServiceException;

public class InsufficientResourcesException extends ServiceException {
    private final long crystalsNeeded;
    private final long gasNeeded;
    private final long energyNeeded;

    private final long crystalsAvailable;
    private final long gasAvailable;
    private final long energyAvailable;

    public InsufficientResourcesException(long crystalsNeeded, long gasNeeded, long energyNeeded, long crystalsAvailable, long gasAvailable, long energyAvailable) {
        this.crystalsNeeded = crystalsNeeded;
        this.gasNeeded = gasNeeded;
        this.energyNeeded = energyNeeded;
        this.crystalsAvailable = crystalsAvailable;
        this.gasAvailable = gasAvailable;
        this.energyAvailable = energyAvailable;
    }

    public long getCrystalsNeeded() {
        return crystalsNeeded;
    }

    public long getGasNeeded() {
        return gasNeeded;
    }

    public long getEnergyNeeded() {
        return energyNeeded;
    }

    public long getCrystalsAvailable() {
        return crystalsAvailable;
    }

    public long getGasAvailable() {
        return gasAvailable;
    }

    public long getEnergyAvailable() {
        return energyAvailable;
    }
}
