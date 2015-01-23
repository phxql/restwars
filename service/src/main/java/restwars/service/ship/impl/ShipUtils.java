package restwars.service.ship.impl;

import restwars.service.infrastructure.UUIDFactory;
import restwars.service.ship.Hangar;
import restwars.service.ship.HangarDAO;
import restwars.service.ship.Ships;

import java.util.Optional;
import java.util.UUID;

public class ShipUtils {
    public long calculateStorageCapacity(Ships ships) {
        return ships.stream().mapToLong(s -> s.getType().getStorageCapacity()).sum();
    }

    /**
     * Finds the speed of the slowest ship.
     *
     * @param ships Ships.
     * @return Speed of the slowest ship.
     */
    public double findSpeedOfSlowestShip(Ships ships) {
        assert ships != null;
        assert !ships.isEmpty();

        return ships.asList().stream().mapToDouble(s -> s.getType().getSpeed()).min().getAsDouble();
    }

    public Hangar getOrCreateHangar(HangarDAO hangarDAO, UUIDFactory uuidFactory, UUID planetId, UUID playerId) {
        Optional<Hangar> mayBeHangar = hangarDAO.findWithPlanetId(planetId);
        Hangar hangar = mayBeHangar.orElse(new Hangar(uuidFactory.create(), planetId, playerId, Ships.EMPTY));

        if (!mayBeHangar.isPresent()) {
            hangarDAO.insert(hangar);
        }

        return hangar;
    }
}
