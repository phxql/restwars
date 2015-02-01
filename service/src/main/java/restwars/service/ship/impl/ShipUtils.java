package restwars.service.ship.impl;

import com.google.common.base.Preconditions;
import restwars.model.ship.Hangar;
import restwars.model.ship.Ships;
import restwars.service.infrastructure.UUIDFactory;
import restwars.service.mechanics.ShipMechanics;
import restwars.service.ship.HangarDAO;

import java.util.Optional;
import java.util.UUID;

public class ShipUtils {
    public long calculateStorageCapacity(Ships ships, ShipMechanics shipMechanics) {
        Preconditions.checkNotNull(ships, "ships");
        Preconditions.checkNotNull(shipMechanics, "shipMechanics");

        return ships.stream().mapToLong(s -> shipMechanics.getCargoSpace(s.getType())).sum();
    }

    /**
     * Finds the speed of the slowest ship.
     *
     * @param ships Ships.
     * @return Speed of the slowest ship.
     */
    public double findSpeedOfSlowestShip(Ships ships, ShipMechanics shipMechanics) {
        Preconditions.checkNotNull(ships, "ships");
        Preconditions.checkNotNull(shipMechanics, "shipMechanics");


        return ships.asList().stream().mapToDouble(s -> shipMechanics.getFlightSpeed(s.getType())).min().getAsDouble();
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
