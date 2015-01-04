package restwars.service.telescope;

import restwars.service.planet.Planet;

import java.util.List;

public interface TelescopeService {
    /**
     * Finds all planets in vicinity of the given planet. The range depends on the level of the telescope on the planet.
     *
     * @param planet Planet where the telescope is located.
     * @return Planets in vicinity.
     */
    List<PlanetWithOwner> scan(Planet planet) throws ScanException;
}
