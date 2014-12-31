package restwars.service.resource;

import com.google.common.base.Objects;

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

    public Resources plus(Resources other) {
        return new Resources(crystals + other.getCrystals(), gas + other.getGas(), energy + other.getEnergy());
    }

    public Resources minus(Resources other) {
        return new Resources(crystals - other.getCrystals(), gas - other.getGas(), energy - other.getEnergy());
    }

    public boolean isEnough(Resources resources) {
        return crystals >= resources.getCrystals() && gas >= resources.getGas() && energy >= resources.getEnergy();
    }

    public boolean isEnoughEnergy(long energy) {
        return this.energy >= energy;
    }

    public static Resources energy(long energy) {
        return new Resources(0, 0, energy);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("crystals", crystals)
                .add("gas", gas)
                .add("energy", energy)
                .toString();
    }
}
