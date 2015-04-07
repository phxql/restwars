package restwars.service.points.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.model.building.Building;
import restwars.model.building.Buildings;
import restwars.model.building.ConstructionSite;
import restwars.model.flight.Flight;
import restwars.model.planet.Planet;
import restwars.model.player.Player;
import restwars.model.points.Points;
import restwars.model.ship.Hangar;
import restwars.model.ship.Ship;
import restwars.model.ship.ShipInConstruction;
import restwars.model.technology.Research;
import restwars.model.technology.Technologies;
import restwars.model.technology.Technology;
import restwars.service.building.BuildingDAO;
import restwars.service.building.ConstructionSiteDAO;
import restwars.service.flight.FlightDAO;
import restwars.service.infrastructure.RoundService;
import restwars.service.infrastructure.UUIDFactory;
import restwars.service.mechanics.BuildingMechanics;
import restwars.service.mechanics.ResourcesMechanics;
import restwars.service.mechanics.ShipMechanics;
import restwars.service.mechanics.TechnologyMechanics;
import restwars.service.planet.PlanetDAO;
import restwars.service.player.PlayerDAO;
import restwars.service.points.PointsDAO;
import restwars.service.points.PointsService;
import restwars.service.ship.HangarDAO;
import restwars.service.ship.ShipInConstructionDAO;
import restwars.service.technology.ResearchDAO;
import restwars.service.technology.TechnologyDAO;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Default implementation for {@link PointsService}.
 * <p>
 * It calculates points based on:
 * * Buildings on planets
 * * Construction sites
 * * Ships on planets
 * * Ships in construction
 * * Resources on planets
 * * Technologies
 * * Researches
 * * Ships on flights
 * * Resources in cargo
 */
public class PointsServiceImpl implements PointsService {
    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PointsServiceImpl.class);

    private final PointsDAO pointsDAO;
    private final PlayerDAO playerDAO;
    private final PlanetDAO planetDAO;
    private final BuildingDAO buildingDAO;
    private final BuildingMechanics buildingMechanics;
    private final ShipMechanics shipMechanics;
    private final RoundService roundService;
    private final UUIDFactory uuidFactory;
    private final HangarDAO hangarDAO;
    private final ResourcesMechanics resourcesMechanics;
    private final TechnologyMechanics technologyMechanics;
    private final TechnologyDAO technologyDAO;
    private final ConstructionSiteDAO constructionSiteDAO;
    private final ShipInConstructionDAO shipInConstructionDAO;
    private final ResearchDAO researchDAO;
    private final FlightDAO flightDAO;

    @Inject
    public PointsServiceImpl(PointsDAO pointsDAO, PlayerDAO playerDAO, PlanetDAO planetDAO, BuildingDAO buildingDAO, BuildingMechanics buildingMechanics, RoundService roundService, UUIDFactory uuidFactory, HangarDAO hangarDAO, ShipMechanics shipMechanics, ResourcesMechanics resourcesMechanics, TechnologyMechanics technologyMechanics, TechnologyDAO technologyDAO, ConstructionSiteDAO constructionSiteDAO, ShipInConstructionDAO shipInConstructionDAO, ResearchDAO researchDAO, FlightDAO flightDAO) {
        this.flightDAO = checkNotNull(flightDAO, "flightDAO");
        this.constructionSiteDAO = checkNotNull(constructionSiteDAO, "constructionSiteDAO");
        this.shipInConstructionDAO = checkNotNull(shipInConstructionDAO, "shipInConstructionDAO");
        this.researchDAO = checkNotNull(researchDAO, "researchDAO");
        this.technologyDAO = checkNotNull(technologyDAO, "technologyDAO");
        this.technologyMechanics = checkNotNull(technologyMechanics, "technologyMechanics");
        this.resourcesMechanics = checkNotNull(resourcesMechanics, "resourcesMechanics");
        this.shipMechanics = checkNotNull(shipMechanics, "shipMechanics");
        this.hangarDAO = checkNotNull(hangarDAO, "hangarDAO");
        this.uuidFactory = checkNotNull(uuidFactory, "uuidFactory");
        this.roundService = checkNotNull(roundService, "roundService");
        this.buildingDAO = checkNotNull(buildingDAO, "buildingDAO");
        this.buildingMechanics = checkNotNull(buildingMechanics, "buildingMechanics");
        this.planetDAO = checkNotNull(planetDAO, "planetDAO");
        this.playerDAO = checkNotNull(playerDAO, "playerDAO");
        this.pointsDAO = checkNotNull(pointsDAO, "pointsDAO");
    }

    @Override
    public void calculatePointsForAllPlayers() {
        LOGGER.debug("Calculating points for all players");

        for (Player player : playerDAO.findAll()) {
            LOGGER.debug("Calculating points for player {}", player.getUsername());

            calculatePointsForPlayer(player);
        }
    }

    private void calculatePointsForPlayer(Player player) {
        assert player != null;

        long points = 0;

        List<Planet> planets = planetDAO.findWithOwnerId(player.getId());
        for (Planet planet : planets) {
            points += calculatePointsForPlanet(planet);
        }

        points += calculatePointsForTechnologies(player);
        points += calculatePointsForFlights(player);

        Points pointsEntity = new Points(uuidFactory.create(), player.getId(), roundService.getCurrentRound(), points);
        pointsDAO.insert(pointsEntity);
    }

    private long calculatePointsForFlights(Player player) {
        assert player != null;

        long points = 0;

        List<Flight> flights = flightDAO.findWithPlayerId(player.getId());
        for (Flight flight : flights) {
            for (Ship ship : flight.getShips()) {
                points += shipMechanics.calculatePointsForShip(ship.getType()) * ship.getAmount();
            }

            points += resourcesMechanics.calculatePointsForResources(flight.getCargo());
        }

        return points;
    }

    private long calculatePointsForTechnologies(Player player) {
        assert player != null;

        long points = 0;

        Technologies technologies = technologyDAO.findAllWithPlayerId(player.getId());
        for (Technology technology : technologies) {
            points += technologyMechanics.calculatePointsForTechnology(technology.getType(), technology.getLevel());
        }

        return points;
    }

    private long calculatePointsForPlanet(Planet planet) {
        assert planet != null;

        long points = 0;

        Buildings buildings = buildingDAO.findWithPlanetId(planet.getId());
        for (Building building : buildings) {
            points += buildingMechanics.calculatePointsForBuilding(building.getType(), building.getLevel());
        }
        List<ConstructionSite> constructionSites = constructionSiteDAO.findWithPlanetId(planet.getId());
        for (ConstructionSite constructionSite : constructionSites) {
            points += buildingMechanics.calculatePointsForConstructionSite(constructionSite.getType(), constructionSite.getLevel());
        }

        Optional<Hangar> hangar = hangarDAO.findWithPlanetId(planet.getId());
        if (hangar.isPresent()) {
            for (Ship ship : hangar.get().getShips()) {
                points += shipMechanics.calculatePointsForShip(ship.getType()) * ship.getAmount();
            }
        }
        List<ShipInConstruction> shipsInConstruction = shipInConstructionDAO.findWithPlanetId(planet.getId());
        for (ShipInConstruction shipInConstruction : shipsInConstruction) {
            points += shipMechanics.calculatePointsForShipInConstruction(shipInConstruction.getType());
        }

        List<Research> researches = researchDAO.findWithPlanetId(planet.getId());
        for (Research research : researches) {
            points += technologyMechanics.calculatePointsForResearch(research.getType(), research.getLevel());
        }

        points += resourcesMechanics.calculatePointsForResources(planet.getResources());

        return points;
    }
}
