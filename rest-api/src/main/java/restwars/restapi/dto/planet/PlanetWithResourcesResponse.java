package restwars.restapi.dto.planet;

import com.google.common.base.Objects;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import restwars.restapi.dto.ResourcesResponse;

@ApiModel(description = "A planet with resources")
public class PlanetWithResourcesResponse {
    @ApiModelProperty(value = "Location", required = true)
    private String location;

    @ApiModelProperty(value = "Amount of crystals", required = true)
    private long crystal;

    @ApiModelProperty(value = "Amount of gas", required = true)
    private long gas;

    @ApiModelProperty(value = "Amount of energy", required = true)
    private long energy;

    @ApiModelProperty(value = "Colonized in round", required = true)
    private long colonizedInRound;

    @ApiModelProperty(value = "Resources gathered per round", required = true)
    private ResourcesResponse resourcesPerRound;

    public PlanetWithResourcesResponse() {
    }

    public PlanetWithResourcesResponse(String location, long crystal, long gas, long energy, long colonizedInRound, ResourcesResponse resourcesPerRound) {

        this.location = location;
        this.crystal = crystal;
        this.gas = gas;
        this.energy = energy;
        this.colonizedInRound = colonizedInRound;
        this.resourcesPerRound = resourcesPerRound;
    }

    public String getLocation() {
        return location;
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

    public void setLocation(String location) {
        this.location = location;
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

    public ResourcesResponse getResourcesPerRound() {
        return resourcesPerRound;
    }

    public void setResourcesPerRound(ResourcesResponse resourcesPerRound) {
        this.resourcesPerRound = resourcesPerRound;
    }

    public long getColonizedInRound() {
        return colonizedInRound;
    }

    public void setColonizedInRound(long colonizedInRound) {
        this.colonizedInRound = colonizedInRound;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("location", location)
                .add("crystal", crystal)
                .add("gas", gas)
                .add("energy", energy)
                .add("colonizedInRound", colonizedInRound)
                .add("resourcesPerRound", resourcesPerRound)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlanetWithResourcesResponse that = (PlanetWithResourcesResponse) o;

        return Objects.equal(this.location, that.location) &&
                Objects.equal(this.crystal, that.crystal) &&
                Objects.equal(this.gas, that.gas) &&
                Objects.equal(this.energy, that.energy) &&
                Objects.equal(this.colonizedInRound, that.colonizedInRound) &&
                Objects.equal(this.resourcesPerRound, that.resourcesPerRound);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(location, crystal, gas, energy, colonizedInRound, resourcesPerRound);
    }
}
