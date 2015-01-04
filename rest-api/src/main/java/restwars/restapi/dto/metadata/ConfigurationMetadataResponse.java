package restwars.restapi.dto.metadata;

import com.google.common.base.Objects;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import restwars.restapi.dto.ResourcesResponse;

@ApiModel(description = "Configuration metadata")
public class ConfigurationMetadataResponse {
    @ApiModelProperty(value = "Galaxy count", required = true)
    private int galaxyCount;
    @ApiModelProperty(value = "Solar systems in a galaxy", required = true)
    private int solarSystemsPerGalaxy;
    @ApiModelProperty(value = "Planets in a solar system", required = true)
    private int planetsPerSolarSystem;
    @ApiModelProperty(value = "Start resources", required = true)
    private ResourcesResponse startingResources;
    @ApiModelProperty(value = "Round time in seconds", required = true)
    private int roundTimeInSeconds;

    public ConfigurationMetadataResponse() {
    }

    public ConfigurationMetadataResponse(int galaxyCount, int solarSystemsPerGalaxy, int planetsPerSolarSystem, ResourcesResponse startingResources, int roundTimeInSeconds) {
        this.galaxyCount = galaxyCount;
        this.solarSystemsPerGalaxy = solarSystemsPerGalaxy;
        this.planetsPerSolarSystem = planetsPerSolarSystem;
        this.startingResources = startingResources;
        this.roundTimeInSeconds = roundTimeInSeconds;
    }

    public int getGalaxyCount() {
        return galaxyCount;
    }

    public void setGalaxyCount(int galaxyCount) {
        this.galaxyCount = galaxyCount;
    }

    public int getSolarSystemsPerGalaxy() {
        return solarSystemsPerGalaxy;
    }

    public void setSolarSystemsPerGalaxy(int solarSystemsPerGalaxy) {
        this.solarSystemsPerGalaxy = solarSystemsPerGalaxy;
    }

    public int getPlanetsPerSolarSystem() {
        return planetsPerSolarSystem;
    }

    public void setPlanetsPerSolarSystem(int planetsPerSolarSystem) {
        this.planetsPerSolarSystem = planetsPerSolarSystem;
    }

    public ResourcesResponse getStartingResources() {
        return startingResources;
    }

    public void setStartingResources(ResourcesResponse startingResources) {
        this.startingResources = startingResources;
    }

    public int getRoundTimeInSeconds() {
        return roundTimeInSeconds;
    }

    public void setRoundTimeInSeconds(int roundTimeInSeconds) {
        this.roundTimeInSeconds = roundTimeInSeconds;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("galaxyCount", galaxyCount)
                .add("solarSystemsPerGalaxy", solarSystemsPerGalaxy)
                .add("planetsPerSolarSystem", planetsPerSolarSystem)
                .add("startingResources", startingResources)
                .add("roundTimeInSeconds", roundTimeInSeconds)
                .toString();
    }
}
