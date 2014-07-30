package restwars.storage.ship;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import restwars.service.ship.ShipInConstruction;
import restwars.service.ship.ShipInConstructionDAO;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class InMemoryShipInConstructionDAO implements ShipInConstructionDAO {
    private final Map<UUID, ShipInConstruction> shipsInConstruction = Maps.newHashMap();

    @Override
    public void insert(ShipInConstruction shipInConstruction) {
        Preconditions.checkNotNull(shipInConstruction, "shipInConstruction");

        shipsInConstruction.put(shipInConstruction.getId(), shipInConstruction);
    }

    @Override
    public List<ShipInConstruction> findWithDone(long round) {
        return shipsInConstruction.values().stream().filter(s -> s.getDone() == round).collect(Collectors.toList());
    }

    @Override
    public void delete(ShipInConstruction shipInConstruction) {
        Preconditions.checkNotNull(shipInConstruction, "shipInConstruction");

        shipsInConstruction.values().remove(shipInConstruction);
    }

    @Override
    public List<ShipInConstruction> findWithPlanetId(UUID planetId) {
        Preconditions.checkNotNull(planetId, "planetId");

        return shipsInConstruction.values().stream().filter(s -> s.getPlanetId().equals(planetId)).collect(Collectors.toList());
    }
}
