package restwars.storage.planet;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import restwars.service.planet.Location;
import restwars.service.planet.Planet;
import restwars.service.planet.PlanetDAO;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class InMemoryPlanetDAO implements PlanetDAO {
    private final Map<UUID, Planet> planets = Maps.newHashMap();

    @Override
    public void insert(Planet planet) {
        Preconditions.checkNotNull(planet, "planet");

        planets.put(planet.getId(), planet);
    }

    @Override
    public Optional<Planet> findWithLocation(Location location) {
        Preconditions.checkNotNull(location, "location");

        return planets.values().stream().filter(p -> p.getLocation().equals(location)).findFirst();
    }

    @Override
    public List<Planet> findWithOwnerId(UUID ownerId) {
        Preconditions.checkNotNull(ownerId, "ownerId");

        return planets.values().stream().filter(p -> p.getOwnerId().equals(Optional.of(ownerId))).collect(Collectors.toList());
    }

    @Override
    public void update(Planet planet) {
        Preconditions.checkNotNull(planet, "planet");

        planets.put(planet.getId(), planet);
    }
}
