package restwars.restapi.dto;

import com.google.common.base.Objects;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Resources")
public class ResourcesResponse {
    @ApiModelProperty(value = "Amount of crystal", required = true)
    private long crystal;

    @ApiModelProperty(value = "Amount of gas", required = true)
    private long gas;

    @ApiModelProperty(value = "Amount of energy", required = true)
    private long energy;

    public ResourcesResponse() {
    }

    public ResourcesResponse(long crystal, long gas, long energy) {
        this.crystal = crystal;
        this.gas = gas;
        this.energy = energy;
    }

    public long getCrystal() {
        return crystal;
    }

    public long getGas() {
        return gas;
    }

    public long getEnergy() {
        return energy;
    }

    public void setCrystal(long crystal) {
        this.crystal = crystal;
    }

    public void setGas(long gas) {
        this.gas = gas;
    }

    public void setEnergy(long energy) {
        this.energy = energy;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("crystal", crystal)
                .add("gas", gas)
                .add("energy", energy)
                .toString();
    }
}
