package restwars.service;

import com.google.common.base.Preconditions;

public class UniverseConfiguration {
    private final int galaxyCount;
    private final int solarSystemsPerGalaxy;
    private final int planetsPerSolarSystem;
    private final long startingCrystals;
    private final long startingGas;
    private final long startingEnergy;
    private final int roundTimeInSeconds;

    public UniverseConfiguration(int galaxyCount, int solarSystemsPerGalaxy, int planetsPerSolarSystem, long startingCrystals, long startingGas, long startingEnergy, int roundTimeInSeconds) {
        Preconditions.checkArgument(startingCrystals >= 0, "startingCrystals must be >= 0");
        Preconditions.checkArgument(startingGas >= 0, "startingGas must be >= 0");
        Preconditions.checkArgument(startingEnergy >= 0, "startingEnergy must be >= 0");
        Preconditions.checkArgument(galaxyCount > 0, "galaxyCount must be > 0");
        Preconditions.checkArgument(solarSystemsPerGalaxy > 0, "solarSystemsPerGalaxy must be > 0");
        Preconditions.checkArgument(planetsPerSolarSystem > 0, "planetsPerSolarSystem must be > 0");
        Preconditions.checkArgument(roundTimeInSeconds > 0, "roundTimeInSeconds must be > 0");

        this.startingCrystals = startingCrystals;
        this.startingGas = startingGas;
        this.startingEnergy = startingEnergy;
        this.galaxyCount = galaxyCount;
        this.solarSystemsPerGalaxy = solarSystemsPerGalaxy;
        this.planetsPerSolarSystem = planetsPerSolarSystem;
        this.roundTimeInSeconds = roundTimeInSeconds;
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

    public long getStartingCrystals() {
        return startingCrystals;
    }

    public long getStartingGas() {
        return startingGas;
    }

    public long getStartingEnergy() {
        return startingEnergy;
    }

    public int getRoundTimeInSeconds() {
        return roundTimeInSeconds;
    }
}
