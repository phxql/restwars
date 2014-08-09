package restwars.storage.planet;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import restwars.service.planet.Location;
import restwars.service.planet.Planet;
import restwars.service.planet.PlanetDAO;

import javax.inject.Singleton;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Singleton
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
    public List<Planet> findAll() {
        return Lists.newArrayList(planets.values());
    }

    @Override
    public Optional<Planet> findWithId(UUID id) {
        Preconditions.checkNotNull(id, "id");

        return Optional.ofNullable(planets.get(id));
    }

    @Override
    public void update(Planet planet) {
        Preconditions.checkNotNull(planet, "planet");

        planets.put(planet.getId(), planet);
    }
}
