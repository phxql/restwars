package restwars.rest.configuration;

/**
 * Debug options.
 */
public class DebugOptions {
    /**
     * If true all flights take 1 round to reach destination.
     */
    private boolean speedUpFlights;
    /**
     * If true all researches take 1 round to complete.
     */
    private boolean speedUpResearches;
    /**
     * If true all buildings take 1 round to complete.
     */
    private boolean speedUpBuildingConstructions;
    /**
     * If true all ships take 1 round to complete.
     */
    private boolean speedUpShipConstructions;
    /**
     * If true, all ships are for free.
     */
    private boolean freeShips;
    /**
     * If true, all researches are for free.
     */
    private boolean freeResearches;
    /**
     * If true, all buildings are for free.
     */
    private boolean freeBuildings;
    /**
     * If true, all flights are for free.
     */
    private boolean freeFlights;
    /**
     * If true, buildings have no prerequisites.
     */
    private boolean noBuildingPrerequisites;
    /**
     * If true, ships have no prerequisites.
     */
    private boolean noShipPrerequisites;
    /**
     * If true, researches have no prerequisites.
     */
    private boolean noResearchPrerequisites;

    public boolean isSpeedUpFlights() {
        return speedUpFlights;
    }

    public void setSpeedUpFlights(boolean speedUpFlights) {
        this.speedUpFlights = speedUpFlights;
    }

    public boolean isSpeedUpResearches() {
        return speedUpResearches;
    }

    public void setSpeedUpResearches(boolean speedUpResearches) {
        this.speedUpResearches = speedUpResearches;
    }

    public boolean isSpeedUpBuildingConstructions() {
        return speedUpBuildingConstructions;
    }

    public void setSpeedUpBuildingConstructions(boolean speedUpBuildingConstructions) {
        this.speedUpBuildingConstructions = speedUpBuildingConstructions;
    }

    public boolean isSpeedUpShipConstructions() {
        return speedUpShipConstructions;
    }

    public void setSpeedUpShipConstructions(boolean speedUpShipConstructions) {
        this.speedUpShipConstructions = speedUpShipConstructions;
    }

    public boolean isFreeShips() {
        return freeShips;
    }

    public void setFreeShips(boolean freeShips) {
        this.freeShips = freeShips;
    }

    public boolean isFreeResearches() {
        return freeResearches;
    }

    public void setFreeResearches(boolean freeResearches) {
        this.freeResearches = freeResearches;
    }

    public boolean isFreeBuildings() {
        return freeBuildings;
    }

    public void setFreeBuildings(boolean freeBuildings) {
        this.freeBuildings = freeBuildings;
    }

    public boolean isFreeFlights() {
        return freeFlights;
    }

    public void setFreeFlights(boolean freeFlights) {
        this.freeFlights = freeFlights;
    }

    public boolean isNoBuildingPrerequisites() {
        return noBuildingPrerequisites;
    }

    public void setNoBuildingPrerequisites(boolean noBuildingPrerequisites) {
        this.noBuildingPrerequisites = noBuildingPrerequisites;
    }

    public boolean isNoShipPrerequisites() {
        return noShipPrerequisites;
    }

    public void setNoShipPrerequisites(boolean noShipPrerequisites) {
        this.noShipPrerequisites = noShipPrerequisites;
    }

    public boolean isNoResearchPrerequisites() {
        return noResearchPrerequisites;
    }

    public void setNoResearchPrerequisites(boolean noResearchPrerequisites) {
        this.noResearchPrerequisites = noResearchPrerequisites;
    }
}
