package restwars.service.telescope.impl;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.service.building.Building;
import restwars.service.building.BuildingDAO;
import restwars.service.building.BuildingType;
import restwars.service.planet.Location;
import restwars.service.planet.Planet;
import restwars.service.planet.PlanetDAO;
import restwars.service.player.Player;
import restwars.service.ship.ShipService;
import restwars.service.telescope.IncomingFlight;
import restwars.service.telescope.PlanetWithOwner;
import restwars.service.telescope.ScanException;
import restwars.service.telescope.TelescopeService;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TelescopeServiceImpl implements TelescopeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TelescopeServiceImpl.class);
    private final PlanetDAO planetDAO;
    private final BuildingDAO buildingDAO;

    @Inject
    public TelescopeServiceImpl(PlanetDAO planetDAO, BuildingDAO buildingDAO, ShipService shipService) {
        this.planetDAO = Preconditions.checkNotNull(planetDAO, "planetDAO");
        this.buildingDAO = Preconditions.checkNotNull(buildingDAO, "buildingDAO");
    }

    @Override
    public List<PlanetWithOwner> scan(Planet planet) throws ScanException {
        Preconditions.checkNotNull(planet, "planet");

        Optional<Building> telescope = buildingDAO.findWithPlanetId(planet.getId()).stream().filter(b -> b.getType().equals(BuildingType.TELESCOPE)).findFirst();
        if (!telescope.isPresent()) {
            throw new ScanException(ScanException.Reason.NO_TELESCOPE);
        }

        int level = telescope.get().getLevel();

        Location location = planet.getLocation();
        int delta = level - 1;
        return planetDAO.findInRange(location.getGalaxy(), location.getGalaxy(), location.getSolarSystem() - delta, location.getSolarSystem() + delta, 0, Integer.MAX_VALUE);
    }

    @Override
    public List<IncomingFlight> findIncomingFlights(Player player) {
        Preconditions.checkNotNull(player, "player");
        LOGGER.debug("Finding incoming flights for {}", player);

        // TODO: Implement this!
        return Collections.emptyList();
    }
}
