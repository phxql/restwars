package restwars.model;

import com.google.common.base.Preconditions;
import restwars.model.resource.Resources;

public class UniverseConfiguration {
    private final int galaxyCount;
    private final int solarSystemsPerGalaxy;
    private final int planetsPerSolarSystem;
    private final Resources startingResources;
    private final int roundTimeInSeconds;
    private final boolean speedUpEverything;

    public UniverseConfiguration(int galaxyCount, int solarSystemsPerGalaxy, int planetsPerSolarSystem, Resources startingResources, int roundTimeInSeconds, boolean speedUpEverything) {
        Preconditions.checkNotNull(startingResources, "startingResources");
        Preconditions.checkArgument(startingResources.getCrystals() >= 0, "Starting crystals must be >= 0");
        Preconditions.checkArgument(startingResources.getGas() >= 0, "Starting gas must be >= 0");
        Preconditions.checkArgument(startingResources.getEnergy() >= 0, "Starting energy must be >= 0");
        Preconditions.checkArgument(galaxyCount > 0, "galaxyCount must be > 0");
        Preconditions.checkArgument(solarSystemsPerGalaxy > 0, "solarSystemsPerGalaxy must be > 0");
        Preconditions.checkArgument(planetsPerSolarSystem > 0, "planetsPerSolarSystem must be > 0");
        Preconditions.checkArgument(roundTimeInSeconds > 0, "roundTimeInSeconds must be > 0");

        this.galaxyCount = galaxyCount;
        this.solarSystemsPerGalaxy = solarSystemsPerGalaxy;
        this.planetsPerSolarSystem = planetsPerSolarSystem;
        this.roundTimeInSeconds = roundTimeInSeconds;
        this.startingResources = startingResources;
        this.speedUpEverything = speedUpEverything;
    }

    public int getGalaxyCount() {
        return galaxyCount;
    }

    public int getSolarSystemsPerGalaxy() {
        return solarSystemsPerGalaxy;
    }

    public int getPlanetsPerSolarSystem() {
        return planetsPerSolarSystem;
    }

    public Resources getStartingResources() {
        return startingResources;
    }

    public int getRoundTimeInSeconds() {
        return roundTimeInSeconds;
    }

    public boolean isSpeedUpEverything() {
        return speedUpEverything;
    }
}
