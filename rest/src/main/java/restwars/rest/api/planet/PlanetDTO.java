package restwars.rest.api.planet;

import com.google.common.base.Preconditions;
import restwars.rest.api.building.BuildingDTO;
import restwars.service.building.Building;
import restwars.service.planet.Planet;

import java.util.List;
import java.util.stream.Collectors;

public class PlanetDTO {
    private final String location;

    private final long crystal;

    private final long gas;

    private final long energy;

    private final List<BuildingDTO> buildings;

    public PlanetDTO(String location, long crystal, long gas, long energy, List<BuildingDTO> buildings) {
        this.location = location;
        this.crystal = crystal;
        this.gas = gas;
        this.energy = energy;
        this.buildings = buildings;
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

    public List<BuildingDTO> getBuildings() {
        return buildings;
    }

    public static PlanetDTO fromPlanet(Planet planet, List<Building> buildings) {
        Preconditions.checkNotNull(planet, "planet");
        Preconditions.checkNotNull(buildings, "buildings");

        return new PlanetDTO(
                planet.getLocation().toString(), planet.getCrystals(), planet.getGas(), planet.getEnergy(),
                buildings.stream().map(BuildingDTO::fromBuilding).collect(Collectors.toList())
        );
    }
}
