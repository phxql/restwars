package restwars.service.planet.impl;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.service.infrastructure.UUIDFactory;
import restwars.service.planet.Location;
import restwars.service.planet.Planet;
import restwars.service.planet.PlanetDAO;
import restwars.service.planet.PlanetService;
import restwars.service.player.Player;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PlanetServiceImpl implements PlanetService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlanetServiceImpl.class);

    private final UUIDFactory uuidFactory;
    private final PlanetDAO planetDAO;

    public PlanetServiceImpl(UUIDFactory uuidFactory, PlanetDAO planetDAO) {
        this.uuidFactory = Preconditions.checkNotNull(uuidFactory, "uuidFactory");
        this.planetDAO = Preconditions.checkNotNull(planetDAO, "planetDAO");
    }

    @Override
    public Planet createPlanet(Location location, Player owner) {
        Preconditions.checkNotNull(location, "location");
        Preconditions.checkNotNull(owner, "owner");

        UUID id = uuidFactory.create();

        Planet planet = new Planet(id, location, Optional.of(owner.getId()));
        planetDAO.insert(planet);

        LOGGER.debug("Created planet {}", planet);

        return planet;
    }

    @Override
    public List<Planet> findWithOwner(Player owner) {
        Preconditions.checkNotNull(owner, "owner");

        return planetDAO.findWithOwnerId(owner.getId());
    }
}
