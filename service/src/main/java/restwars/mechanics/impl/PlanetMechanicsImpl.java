package restwars.mechanics.impl;

import com.google.common.collect.Maps;
import restwars.mechanics.PlanetMechanics;
import restwars.service.building.BuildingType;
import restwars.service.resource.Resources;

import java.util.Map;

public class PlanetMechanicsImpl implements PlanetMechanics {
    private final Resources starterPlanetResources;
    private final Map<BuildingType, Integer> starterPlanetBuildings;
    private final Resources colonizedPlanetResources;
    private final Map<BuildingType, Integer> colonizedPlanetBuildings;

    public PlanetMechanicsImpl(Resources starterPlanetResources, Map<BuildingType, Integer> starterPlanetBuildings, Resources colonizedPlanetResources, Map<BuildingType, Integer> colonizedPlanetBuildings) {
        this.starterPlanetResources = starterPlanetResources;
        this.starterPlanetBuildings = starterPlanetBuildings;
        this.colonizedPlanetResources = colonizedPlanetResources;
        this.colonizedPlanetBuildings = colonizedPlanetBuildings;
    }

    @Override
    public Map<BuildingType, Integer> getStarterPlanetBuildings() {
        return starterPlanetBuildings;
    }

    @Override
    public Resources getStarterPlanetResources() {
        return starterPlanetResources;
    }

    @Override
    public Resources getColonizedPlanetResources() {
        return colonizedPlanetResources;
    }

    @Override
    public Map<BuildingType, Integer> getColonizedPlanetBuildings() {
        return colonizedPlanetBuildings;
    }

    public static PlanetMechanics create() {
        Resources starterPlanetResources = new Resources(200, 100, 800);

        Map<BuildingType, Integer> starterPlanetBuildings = Maps.newHashMap();
        starterPlanetBuildings.put(BuildingType.COMMAND_CENTER, 1);
        starterPlanetBuildings.put(BuildingType.CRYSTAL_MINE, 1);
        starterPlanetBuildings.put(BuildingType.GAS_REFINERY, 1);
        starterPlanetBuildings.put(BuildingType.SOLAR_PANELS, 1);

        Resources colonizedPlanetResources = new Resources(100, 50, 400);
        Map<BuildingType, Integer> colonizedPlanetBuildings = Maps.newHashMap();
        colonizedPlanetBuildings.put(BuildingType.COMMAND_CENTER, 1);

        return new PlanetMechanicsImpl(starterPlanetResources, starterPlanetBuildings, colonizedPlanetResources, colonizedPlanetBuildings);
    }
}
