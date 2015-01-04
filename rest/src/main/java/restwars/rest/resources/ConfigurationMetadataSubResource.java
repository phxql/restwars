package restwars.rest.resources;

import com.google.common.base.Preconditions;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import restwars.rest.mapper.ConfigurationMapper;
import restwars.restapi.dto.metadata.ConfigurationMetadataResponse;
import restwars.service.UniverseConfiguration;

import javax.inject.Inject;
import javax.ws.rs.GET;

@Api(value = "/configuration", hidden = true)
public class ConfigurationMetadataSubResource {
    private final UniverseConfiguration universeConfiguration;

    @Inject
    public ConfigurationMetadataSubResource(UniverseConfiguration universeConfiguration) {
        this.universeConfiguration = Preconditions.checkNotNull(universeConfiguration, "universeConfiguration");
    }

    @GET
    @ApiOperation("Configuration")
    public ConfigurationMetadataResponse getConfiguration() {
        return ConfigurationMapper.fromConfiguration(universeConfiguration);
    }
}
