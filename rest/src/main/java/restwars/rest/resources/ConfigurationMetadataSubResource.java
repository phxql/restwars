package restwars.rest.resources;

import com.google.common.base.Preconditions;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import restwars.rest.mapper.ConfigurationMapper;
import restwars.restapi.dto.metadata.ConfigurationMetadataResponse;
import restwars.service.UniverseConfiguration;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Subresource for configuration metadata.
 */
@Api(value = "/configuration", hidden = true)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ConfigurationMetadataSubResource {
    private final UniverseConfiguration universeConfiguration;

    @Inject
    public ConfigurationMetadataSubResource(UniverseConfiguration universeConfiguration) {
        this.universeConfiguration = Preconditions.checkNotNull(universeConfiguration, "universeConfiguration");
    }

    /**
     * Returns the current server configuration.
     *
     * @return Server configuration.
     */
    @GET
    @ApiOperation("Configuration")
    public ConfigurationMetadataResponse getConfiguration() {
        return ConfigurationMapper.fromConfiguration(universeConfiguration);
    }
}
