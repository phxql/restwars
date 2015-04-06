package restwars.model;

import com.google.common.base.Preconditions;
import restwars.model.resource.Resources;

public class UniverseConfiguration {
    private final int galaxyCount;
    private final int solarSystemsPerGalaxy;
    private final int planetsPerSolarSystem;
    private final Resources startingResources;
    private final int roundTimeInSeconds;
    /**
     * If true all flights take 1 round to reach destination.
     */
    private final boolean speedUpFlights;
    /**
     * If true all researches take 1 round to complete.
     */
    private final boolean speedUpResearches;
    /**
     * If true all buildings take 1 round to complete.
     */
    private final boolean speedUpBuildingConstructions;
    /**
     * If true all ships take 1 round to complete.
     */
    private final boolean speedUpShipConstructions;

    /**
     * If true, all ships are for free.
     */
    private final boolean freeShips;
    /**
     * If true, all researches are for free.
     */
    private final boolean freeResearches;
    /**
     * If true, all buildings are for free.
     */
    private final boolean freeBuildings;
    /**
     * If true, all flights are for free.
     */
    private final boolean freeFlights;

    /**
     * If true, buildings have no prerequisites.
     */
    private final boolean noBuildingPrerequisites;
    /**
     * If true, ships have no prerequisites.
     */
    private final boolean noShipPrerequisites;
    /**
     * If true, researches have no prerequisites.
     */
    private final boolean noResearchPrerequisites;

    /**
     * Points are calculated every X rounds.
     */
    private final int calculatePointsEvery;

    public UniverseConfiguration(int galaxyCount, int solarSystemsPerGalaxy, int planetsPerSolarSystem, Resources startingResources, int roundTimeInSeconds, boolean speedUpFlights, boolean speedUpResearches, boolean speedUpBuildingConstructions, boolean speedUpShipConstructions, boolean freeShips, boolean freeResearches, boolean freeBuildings, boolean freeFlights, boolean noBuildingPrerequisites, boolean noShipPrerequisites, boolean noResearchPrerequisites, int calculatePointsEvery) {
        Preconditions.checkNotNull(startingResources, "startingResources");
        Preconditions.checkArgument(startingResources.getCrystals() >= 0, "Starting crystals must be >= 0");
        Preconditions.checkArgument(startingResources.getGas() >= 0, "Starting gas must be >= 0");
        Preconditions.checkArgument(startingResources.getEnergy() >= 0, "Starting energy must be >= 0");
        Preconditions.checkArgument(galaxyCount > 0, "galaxyCount must be > 0");
        Preconditions.checkArgument(solarSystemsPerGalaxy > 0, "solarSystemsPerGalaxy must be > 0");
        Preconditions.checkArgument(planetsPerSolarSystem > 0, "planetsPerSolarSystem must be > 0");
        Preconditions.checkArgument(roundTimeInSeconds > 0, "roundTimeInSeconds must be > 0");
        Preconditions.checkArgument(calculatePointsEvery > 0, "calculatePointsEvery must be > 0");

        this.speedUpFlights = speedUpFlights;
        this.speedUpResearches = speedUpResearches;
        this.speedUpBuildingConstructions = speedUpBuildingConstructions;
        this.speedUpShipConstructions = speedUpShipConstructions;
        this.freeShips = freeShips;
        this.freeResearches = freeResearches;
        this.freeBuildings = freeBuildings;
        this.freeFlights = freeFlights;
        this.noBuildingPrerequisites = noBuildingPrerequisites;
        this.noShipPrerequisites = noShipPrerequisites;
        this.noResearchPrerequisites = noResearchPrerequisites;
        this.calculatePointsEvery = calculatePointsEvery;
        this.galaxyCount = galaxyCount;
        this.solarSystemsPerGalaxy = solarSystemsPerGalaxy;
        this.planetsPerSolarSystem = planetsPerSolarSystem;
        this.roundTimeInSeconds = roundTimeInSeconds;
        this.startingResources = startingResources;
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

    public boolean isSpeedUpFlights() {
        return speedUpFlights;
    }

    public boolean isSpeedUpResearches() {
        return speedUpResearches;
    }

    public boolean isSpeedUpBuildingConstructions() {
        return speedUpBuildingConstructions;
    }

    public boolean isSpeedUpShipConstructions() {
        return speedUpShipConstructions;
    }

    public boolean isFreeShips() {
        return freeShips;
    }

    public boolean isFreeResearches() {
        return freeResearches;
    }

    public boolean isFreeBuildings() {
        return freeBuildings;
    }

    public boolean isFreeFlights() {
        return freeFlights;
    }

    public boolean isNoBuildingPrerequisites() {
        return noBuildingPrerequisites;
    }

    public boolean isNoShipPrerequisites() {
        return noShipPrerequisites;
    }

    public boolean isNoResearchPrerequisites() {
        return noResearchPrerequisites;
    }

    public int getCalculatePointsEvery() {
        return calculatePointsEvery;
    }

    public boolean isDebugOptionEnabled() {
        return speedUpFlights || speedUpResearches || speedUpShipConstructions || speedUpBuildingConstructions ||
                freeShips || freeResearches || freeBuildings || freeFlights || noBuildingPrerequisites ||
                noShipPrerequisites || noResearchPrerequisites;
    }
}
