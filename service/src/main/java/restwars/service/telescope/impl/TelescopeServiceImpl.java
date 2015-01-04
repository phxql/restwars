package restwars.service.telescope.impl;

import com.google.common.base.Preconditions;
import restwars.service.building.Building;
import restwars.service.building.BuildingDAO;
import restwars.service.building.BuildingType;
import restwars.service.planet.Location;
import restwars.service.planet.Planet;
import restwars.service.planet.PlanetDAO;
import restwars.service.telescope.PlanetWithOwner;
import restwars.service.telescope.ScanException;
import restwars.service.telescope.TelescopeService;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class TelescopeServiceImpl implements TelescopeService {
    private final PlanetDAO planetDAO;
    private final BuildingDAO buildingDAO;

    @Inject
    public TelescopeServiceImpl(PlanetDAO planetDAO, BuildingDAO buildingDAO) {
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
}
