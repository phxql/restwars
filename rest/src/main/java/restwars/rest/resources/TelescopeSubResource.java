package restwars.rest.resources;

import com.google.common.base.Preconditions;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import io.dropwizard.auth.Auth;
import restwars.rest.mapper.PlanetMapper;
import restwars.rest.resources.param.LocationParam;
import restwars.rest.util.Helper;
import restwars.restapi.dto.planet.PlanetScanResponse;
import restwars.service.planet.Planet;
import restwars.service.planet.PlanetService;
import restwars.service.player.Player;
import restwars.service.telescope.ScanException;
import restwars.service.telescope.TelescopeService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import java.util.List;

@Api(value = "/{location}/telescope", hidden = true)
public class TelescopeSubResource {
    private final PlanetService planetService;
    private final TelescopeService telescopeService;

    @Inject
    public TelescopeSubResource(TelescopeService telescopeService, PlanetService planetService) {
        this.planetService = Preconditions.checkNotNull(planetService, "planetService");
        this.telescopeService = Preconditions.checkNotNull(telescopeService, "telescopeService");
    }

    @GET
    @ApiOperation("Lists all planets in vicinity")
    public List<PlanetScanResponse> scan(
            @Auth @ApiParam(access = "internal") Player player,
            @PathParam("location") @ApiParam("Planet location") LocationParam location
    ) {
        Preconditions.checkNotNull(player, "player");
        Preconditions.checkNotNull(location, "location");

        Planet planet = Helper.findPlanetWithLocationAndOwner(planetService, location.getValue(), player);
        try {
            return Helper.mapToList(telescopeService.scan(planet), PlanetMapper::fromPlanetWithOwner);
        } catch (ScanException e) {
            throw new ScanWebException(e.getReason());
        }
    }
}
