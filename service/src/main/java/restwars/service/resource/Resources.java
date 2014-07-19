package restwars.service.resource;

public class Resources {
    public static final Resources NONE = new Resources(0, 0, 0);

    private final long crystals;

    private final long gas;

    private final long energy;

    public Resources(long crystals, long gas, long energy) {
        this.crystals = crystals;
        this.gas = gas;
        this.energy = energy;
    }

    public long getCrystals() {
        return crystals;
    }

    public long getGas() {
        return gas;
    }

    public long getEnergy() {
        return energy;
    }
}
