package restwars.service.ship.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import restwars.service.infrastructure.RoundService;
import restwars.service.infrastructure.UUIDFactory;
import restwars.service.planet.Planet;
import restwars.service.planet.PlanetDAO;
import restwars.service.player.Player;
import restwars.service.resource.InsufficientResourcesException;
import restwars.service.resource.Resources;
import restwars.service.ship.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ShipServiceImpl implements ShipService {
    private final UUIDFactory uuidFactory;
    private final HangarDAO hangarDAO;
    private final ShipInConstructionDAO shipInConstructionDAO;
    private final PlanetDAO planetDAO;
    private final RoundService roundService;

    public ShipServiceImpl(HangarDAO hangarDAO, ShipInConstructionDAO shipInConstructionDAO, PlanetDAO planetDAO, UUIDFactory uuidFactory, RoundService roundService) {
        this.roundService = Preconditions.checkNotNull(roundService, "roundService");
        this.uuidFactory = Preconditions.checkNotNull(uuidFactory, "uuidFactory");
        this.planetDAO = Preconditions.checkNotNull(planetDAO, "planetDAO");
        this.hangarDAO = Preconditions.checkNotNull(hangarDAO, "hangarDAO");
        this.shipInConstructionDAO = Preconditions.checkNotNull(shipInConstructionDAO, "shipInConstructionDAO");
    }

    @Override
    public ShipInConstruction buildShip(Player player, Planet planet, ShipType type) throws InsufficientResourcesException {
        Preconditions.checkNotNull(player, "player");
        Preconditions.checkNotNull(planet, "planet");
        Preconditions.checkNotNull(type, "type");

        // TODO: Check build queues
        // TODO: Check if the planet has a shipyard

        Resources buildCost = calculateBuildCost(type);
        if (!planet.hasResources(buildCost)) {
            throw new InsufficientResourcesException(buildCost.getCrystals(), buildCost.getGas(), buildCost.getEnergy(), planet.getCrystals(), planet.getGas(), planet.getEnergy());
        }

        Planet updatedPlanet = planet.withResources(planet.getCrystals() - buildCost.getCrystals(), planet.getGas() - buildCost.getGas(), planet.getEnergy() - buildCost.getEnergy());
        planetDAO.update(updatedPlanet);

        UUID id = uuidFactory.create();
        long buildTime = calculateBuildTime(type);
        long currentRound = roundService.getCurrentRound();
        ShipInConstruction shipInConstruction = new ShipInConstruction(id, type, planet.getId(), player.getId(), currentRound, currentRound + buildTime);
        shipInConstructionDAO.insert(shipInConstruction);

        return shipInConstruction;
    }

    @Override
    public long calculateBuildTime(ShipType type) {
        Preconditions.checkNotNull(type, "type");

        switch (type) {
            case MOSQUITO:
                return 1;
            default:
                throw new AssertionError("Unknown ship type: " + type);
        }
    }

    @Override
    public Resources calculateBuildCost(ShipType type) {
        Preconditions.checkNotNull(type, "type");

        switch (type) {
            case MOSQUITO:
                return new Resources(1, 1, 1);
            default:
                throw new AssertionError("Unknown ship type: " + type);
        }
    }

    @Override
    public void finishShipsInConstruction() {
        long round = roundService.getCurrentRound();

        List<ShipInConstruction> doneShips = shipInConstructionDAO.findWithDone(round);
        for (ShipInConstruction ship : doneShips) {
            Optional<Hangar> mayBeHangar = hangarDAO.findWithPlanetId(ship.getPlanetId());
            Hangar hangar = mayBeHangar.orElse(new Hangar(uuidFactory.create(), ship.getPlanetId(), ship.getPlayerId(), Maps.newHashMap()));
            boolean insert = !mayBeHangar.isPresent();

            Long shipCount = hangar.getShips().get(ship.getType());
            if (shipCount == null) {
                shipCount = 0L;
            }
            shipCount += 1;
            hangar.getShips().put(ship.getType(), shipCount);

            if (insert) {
                hangarDAO.insert(hangar);
            } else {
                hangarDAO.update(hangar);
            }
        }
    }
}
