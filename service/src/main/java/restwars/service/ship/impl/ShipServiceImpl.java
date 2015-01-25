package restwars.service.ship.impl;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.service.building.BuildingDAO;
import restwars.service.building.BuildingType;
import restwars.service.building.Buildings;
import restwars.service.event.EventService;
import restwars.service.infrastructure.RoundService;
import restwars.service.infrastructure.UUIDFactory;
import restwars.service.mechanics.BuildingMechanics;
import restwars.service.mechanics.ShipMechanics;
import restwars.service.planet.Planet;
import restwars.service.planet.PlanetDAO;
import restwars.service.player.Player;
import restwars.service.resource.Resources;
import restwars.service.ship.*;
import restwars.service.technology.Technologies;
import restwars.service.technology.TechnologyDAO;
import restwars.service.techtree.Prerequisites;
import restwars.util.MathExt;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ShipServiceImpl implements ShipService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShipServiceImpl.class);

    private final UUIDFactory uuidFactory;
    private final HangarDAO hangarDAO;
    private final ShipInConstructionDAO shipInConstructionDAO;
    private final PlanetDAO planetDAO;
    private final RoundService roundService;
    private final BuildingDAO buildingDAO;
    private final EventService eventService;
    private final TechnologyDAO technologyDAO;
    private final BuildingMechanics buildingMechanics;
    private final ShipMechanics shipMechanics;

    private final ShipUtils shipUtils;

    @Inject
    public ShipServiceImpl(HangarDAO hangarDAO, ShipInConstructionDAO shipInConstructionDAO, PlanetDAO planetDAO, UUIDFactory uuidFactory, RoundService roundService, BuildingDAO buildingDAO, EventService eventService, TechnologyDAO technologyDAO, BuildingMechanics buildingMechanics, ShipMechanics shipMechanics) {
        this.roundService = Preconditions.checkNotNull(roundService, "roundService");
        this.uuidFactory = Preconditions.checkNotNull(uuidFactory, "uuidFactory");
        this.planetDAO = Preconditions.checkNotNull(planetDAO, "planetDAO");
        this.hangarDAO = Preconditions.checkNotNull(hangarDAO, "hangarDAO");
        this.shipInConstructionDAO = Preconditions.checkNotNull(shipInConstructionDAO, "shipInConstructionDAO");
        this.buildingDAO = Preconditions.checkNotNull(buildingDAO, "buildingDAO");
        this.eventService = Preconditions.checkNotNull(eventService, "eventService");
        this.buildingMechanics = Preconditions.checkNotNull(buildingMechanics, "buildingMechanics");
        this.shipMechanics = Preconditions.checkNotNull(shipMechanics, "shipMechanics");
        this.technologyDAO = Preconditions.checkNotNull(technologyDAO, "technologyDAO");

        shipUtils = new ShipUtils();
    }

    @Override
    public List<ShipInConstruction> findShipsInConstructionOnPlanet(Planet planet) {
        Preconditions.checkNotNull(planet, "planet");

        return shipInConstructionDAO.findWithPlanetId(planet.getId());
    }

    @Override
    public ShipInConstruction buildShip(Player player, Planet planet, ShipType type) throws BuildShipException {
        Preconditions.checkNotNull(player, "player");
        Preconditions.checkNotNull(planet, "planet");
        Preconditions.checkNotNull(type, "type");

        Buildings buildings = buildingDAO.findWithPlanetId(planet.getId());
        Technologies technologies = technologyDAO.findAllWithPlayerId(player.getId());

        if (!buildings.has(BuildingType.SHIPYARD)) {
            throw new BuildShipException(BuildShipException.Reason.NO_SHIPYARD);
        }

        // Check prerequisites
        boolean prerequisitesFulfilled = shipMechanics.getPrerequisites(type).fulfilled(
                buildings.stream().map(b -> new Prerequisites.Building(b.getType(), b.getLevel())).collect(Collectors.toList()),
                technologies.stream().map(t -> new Prerequisites.Technology(t.getType(), t.getLevel())).collect(Collectors.toList())
        );
        if (!prerequisitesFulfilled) {
            throw new BuildShipException(BuildShipException.Reason.PREREQUISITES_NOT_FULFILLED);
        }

        List<ShipInConstruction> shipsInConstruction = shipInConstructionDAO.findWithPlanetId(planet.getId());
        if (!shipsInConstruction.isEmpty()) {
            throw new BuildShipException(BuildShipException.Reason.NOT_ENOUGH_BUILD_QUEUES);
        }

        Resources buildCost = shipMechanics.getBuildCost(type);
        if (!planet.getResources().isEnough(buildCost)) {
            throw new BuildShipException(BuildShipException.Reason.INSUFFICIENT_RESOURCES);
        }

        Planet updatedPlanet = planet.withResources(planet.getResources().minus(buildCost));
        planetDAO.update(updatedPlanet);

        int shipyardLevel = buildings.getLevel(BuildingType.SHIPYARD);
        double speedup = 1 - buildingMechanics.calculateShipBuildTimeSpeedup(shipyardLevel);
        long buildTime = Math.max(MathExt.floorLong(shipMechanics.getBuildTime(type) * speedup), 1);

        long currentRound = roundService.getCurrentRound();
        ShipInConstruction shipInConstruction = new ShipInConstruction(uuidFactory.create(), type, planet.getId(), player.getId(), currentRound, currentRound + buildTime);
        shipInConstructionDAO.insert(shipInConstruction);

        return shipInConstruction;
    }

    @Override
    public Ships findShipsOnPlanet(Planet planet) {
        Preconditions.checkNotNull(planet, "planet");

        Optional<Hangar> hangar = hangarDAO.findWithPlanetId(planet.getId());
        if (hangar.isPresent()) {
            return hangar.get().getShips();
        } else {
            return Ships.EMPTY;
        }
    }

    @Override
    public void manifestShips(Player player, Planet planet, Ships ships) {
        Preconditions.checkNotNull(player, "player");
        Preconditions.checkNotNull(planet, "planet");
        Preconditions.checkNotNull(ships, "ships");

        Hangar hangar = shipUtils.getOrCreateHangar(hangarDAO, uuidFactory, planet.getId(), player.getId());

        Hangar updatedHangar = hangar.withShips(hangar.getShips().plus(ships));
        hangarDAO.update(updatedHangar);
    }

    @Override
    public void finishShipsInConstruction() {
        long round = roundService.getCurrentRound();

        List<ShipInConstruction> doneShips = shipInConstructionDAO.findWithDone(round);
        for (ShipInConstruction ship : doneShips) {
            Hangar hangar = shipUtils.getOrCreateHangar(hangarDAO, uuidFactory, ship.getPlanetId(), ship.getPlayerId());

            Hangar updatedHangar = hangar.withShips(hangar.getShips().plus(ship.getType(), 1));
            hangarDAO.update(updatedHangar);
            shipInConstructionDAO.delete(ship);

            // Create event
            eventService.createShipCompletedEvent(ship.getPlayerId(), ship.getPlanetId());
        }
    }
}
