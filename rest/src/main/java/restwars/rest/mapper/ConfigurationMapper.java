package restwars.rest.mapper;

import com.google.common.base.Preconditions;
import restwars.restapi.dto.metadata.ConfigurationMetadataResponse;
import restwars.service.UniverseConfiguration;

/**
 * Maps configuration objects to DTOs and vice versa.
 */
public final class ConfigurationMapper {
    private ConfigurationMapper() {
    }

    public static ConfigurationMetadataResponse fromConfiguration(UniverseConfiguration universeConfiguration) {
        Preconditions.checkNotNull(universeConfiguration, "universeConfiguration");

        return new ConfigurationMetadataResponse(
                universeConfiguration.getGalaxyCount(), universeConfiguration.getSolarSystemsPerGalaxy(), universeConfiguration.getPlanetsPerSolarSystem(),
                ResourcesMapper.fromResources(universeConfiguration.getStartingResources()), universeConfiguration.getRoundTimeInSeconds()
        );
    }
}
