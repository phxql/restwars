package restwars.storage.building;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import restwars.service.building.Building;
import restwars.service.building.BuildingDAO;
import restwars.service.building.BuildingType;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class InMemoryBuildingDAO implements BuildingDAO {
    private final Map<UUID, Building> buildings = Maps.newHashMap();

    @Override
    public List<Building> findWithPlanetId(UUID planetId) {
        Preconditions.checkNotNull(planetId, "planetId");

        return buildings.values().stream().filter(b -> b.getPlanetId().equals(planetId)).collect(Collectors.toList());
    }

    @Override
    public void insert(Building building) {
        Preconditions.checkNotNull(building, "building");

        buildings.put(building.getId(), building);
    }

    @Override
    public void update(Building building) {
        Preconditions.checkNotNull(building, "building");

        buildings.put(building.getId(), building);
    }

    @Override
    public Optional<Building> findWithPlanetIdAndType(UUID planetId, BuildingType type) {
        Preconditions.checkNotNull(planetId, "planetId");
        Preconditions.checkNotNull(type, "type");

        return buildings.values().stream().filter(b -> b.getPlanetId().equals(planetId) && b.getType().equals(type)).findFirst();
    }
}
