package restwars.restapi.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Resources")
public class ResourcesResponse {
    @ApiModelProperty(value = "Amount of crystal", required = true)
    private final long crystal;

    @ApiModelProperty(value = "Amount of gas", required = true)
    private final long gas;

    @ApiModelProperty(value = "Amount of energy", required = true)
    private final long energy;

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
}
