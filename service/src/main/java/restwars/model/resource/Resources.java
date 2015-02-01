package restwars.model.resource;

import com.google.common.base.Objects;

import java.io.Serializable;

public class Resources implements Serializable {
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

    public boolean containsEnergy() {
        return energy > 0;
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

    public boolean isEmpty() {
        return energy == 0 && crystals == 0 && gas == 0;
    }

    public long sum() {
        return crystals + gas + energy;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("crystals", crystals)
                .add("gas", gas)
                .add("energy", energy)
                .toString();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resources that = (Resources) o;

        return Objects.equal(this.crystals, that.crystals) &&
                Objects.equal(this.gas, that.gas) &&
                Objects.equal(this.energy, that.energy);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(crystals, gas, energy);
    }
}
