package restwars.service.points.impl;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.model.building.Building;
import restwars.model.building.Buildings;
import restwars.model.planet.Planet;
import restwars.model.player.Player;
import restwars.model.points.Points;
import restwars.model.ship.Hangar;
import restwars.model.ship.Ship;
import restwars.model.technology.Technologies;
import restwars.model.technology.Technology;
import restwars.service.building.BuildingDAO;
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
import restwars.service.technology.TechnologyDAO;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Default implementation for {@link PointsService}.
 * <p>
 * It calculates points based on:
 * * Buildings on planets
 * * Ships on planets
 * * Resources on planets
 * * Technologies
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

    @Inject
    public PointsServiceImpl(PointsDAO pointsDAO, PlayerDAO playerDAO, PlanetDAO planetDAO, BuildingDAO buildingDAO, BuildingMechanics buildingMechanics, RoundService roundService, UUIDFactory uuidFactory, HangarDAO hangarDAO, ShipMechanics shipMechanics, ResourcesMechanics resourcesMechanics, TechnologyMechanics technologyMechanics, TechnologyDAO technologyDAO) {
        this.technologyDAO = Preconditions.checkNotNull(technologyDAO, "technologyDAO");
        this.technologyMechanics = Preconditions.checkNotNull(technologyMechanics, "technologyMechanics");
        this.resourcesMechanics = Preconditions.checkNotNull(resourcesMechanics, "resourcesMechanics");
        this.shipMechanics = Preconditions.checkNotNull(shipMechanics, "shipMechanics");
        this.hangarDAO = Preconditions.checkNotNull(hangarDAO, "hangarDAO");
        this.uuidFactory = Preconditions.checkNotNull(uuidFactory, "uuidFactory");
        this.roundService = Preconditions.checkNotNull(roundService, "roundService");
        this.buildingDAO = Preconditions.checkNotNull(buildingDAO, "buildingDAO");
        this.buildingMechanics = Preconditions.checkNotNull(buildingMechanics, "buildingMechanics");
        this.planetDAO = Preconditions.checkNotNull(planetDAO, "planetDAO");
        this.playerDAO = Preconditions.checkNotNull(playerDAO, "playerDAO");
        this.pointsDAO = Preconditions.checkNotNull(pointsDAO, "pointsDAO");
    }

    @Override
    public void calculatePointsForAllPlayers() {
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

        Points pointsEntity = new Points(uuidFactory.create(), player.getId(), roundService.getCurrentRound(), points);
        pointsDAO.insert(pointsEntity);
    }

    private long calculatePointsForTechnologies(Player player) {
        long points = 0;

        Technologies technologies = technologyDAO.findAllWithPlayerId(player.getId());
        for (Technology technology : technologies) {
            points += technologyMechanics.calculatePointsForTechnology(technology.getType(), technology.getLevel());
        }

        return points;
    }

    private long calculatePointsForPlanet(Planet planet) {
        long points = 0;

        Buildings buildings = buildingDAO.findWithPlanetId(planet.getId());
        for (Building building : buildings) {
            points += buildingMechanics.calculatePointsForBuilding(building.getType(), building.getLevel());
        }

        Optional<Hangar> hangar = hangarDAO.findWithPlanetId(planet.getId());
        if (hangar.isPresent()) {
            for (Ship ship : hangar.get().getShips()) {
                points += shipMechanics.calculatePointsForShip(ship.getType()) * ship.getAmount();
            }
        }

        points += resourcesMechanics.calculatePointsForResources(planet.getResources());

        return points;
    }
}
