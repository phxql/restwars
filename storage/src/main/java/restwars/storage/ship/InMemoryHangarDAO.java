package restwars.storage.ship;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import restwars.service.ship.Hangar;
import restwars.service.ship.HangarDAO;

import javax.inject.Singleton;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Singleton
public class InMemoryHangarDAO implements HangarDAO {
    private final Map<UUID, Hangar> hangars = Maps.newHashMap();

    @Override
    public Optional<Hangar> findWithPlanetId(UUID planetId) {
        Preconditions.checkNotNull(planetId, "planetId");

        return hangars.values().stream().filter(h -> h.getPlanetId().equals(planetId)).findFirst();
    }

    @Override
    public void update(Hangar hangar) {
        Preconditions.checkNotNull(hangar, "hangar");

        hangars.put(hangar.getId(), hangar);
    }

    @Override
    public void insert(Hangar hangar) {
        Preconditions.checkNotNull(hangar, "hangar");

        hangars.put(hangar.getId(), hangar);
    }
}
