package restwars.rest.api.planet;

import com.google.common.base.Preconditions;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import restwars.service.planet.Planet;

@ApiModel(description = "A planet")
public class PlanetResponse {
    @ApiModelProperty(value = "Location", required = true)
    private final String location;

    @ApiModelProperty(value = "Amount of crystals", required = true)
    private final long crystal;

    @ApiModelProperty(value = "Amount of gas", required = true)
    private final long gas;

    @ApiModelProperty(value = "Amount of energy", required = true)
    private final long energy;

    public PlanetResponse(String location, long crystal, long gas, long energy) {
        this.location = location;
        this.crystal = crystal;
        this.gas = gas;
        this.energy = energy;
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

    public static PlanetResponse fromPlanet(Planet planet) {
        Preconditions.checkNotNull(planet, "planet");

        return new PlanetResponse(
                planet.getLocation().toString(), planet.getResources().getCrystals(), planet.getResources().getGas(), planet.getResources().getEnergy()
        );
    }
}
