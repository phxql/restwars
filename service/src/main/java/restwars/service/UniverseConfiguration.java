package restwars.service;

import com.google.common.base.Preconditions;

public class UniverseConfiguration {
    private final int galaxyCount;

    private final int solarSystemsPerGalaxy;

    private final int planetsPerSolarSystem;

    public UniverseConfiguration(int galaxyCount, int solarSystemsPerGalaxy, int planetsPerSolarSystem) {
        Preconditions.checkArgument(galaxyCount > 0, "galaxyCount must be > 0");
        Preconditions.checkArgument(solarSystemsPerGalaxy > 0, "solarSystemsPerGalaxy must be > 0");
        Preconditions.checkArgument(planetsPerSolarSystem > 0, "planetsPerSolarSystem must be > 0");

        this.galaxyCount = galaxyCount;
        this.solarSystemsPerGalaxy = solarSystemsPerGalaxy;
        this.planetsPerSolarSystem = planetsPerSolarSystem;
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
}
