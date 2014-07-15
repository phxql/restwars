package restwars.rest.resources;

import com.google.common.base.Preconditions;
import io.dropwizard.auth.Auth;
import restwars.rest.api.planet.PlanetDTO;
import restwars.rest.resources.param.LocationParam;
import restwars.service.planet.Planet;
import restwars.service.planet.PlanetService;
import restwars.service.player.Player;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/v1/planet")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PlanetResource {
    private final PlanetService planetService;
    private final BuildingSubResource buildingSubResource;

    public PlanetResource(PlanetService planetService, BuildingSubResource buildingSubResource) {
        this.buildingSubResource = Preconditions.checkNotNull(buildingSubResource, "buildingSubResource");
        this.planetService = Preconditions.checkNotNull(planetService, "planetService");
    }

    @GET
    public List<PlanetDTO> index(@Auth Player player) {
        Preconditions.checkNotNull(player, "player");
        List<Planet> planets = planetService.findWithOwner(player);

        return planets.stream().map(PlanetDTO::fromPlanet).collect(Collectors.toList());
    }

    @GET
    @Path("/{location}")
    public PlanetDTO getPlanet(@Auth Player player, @PathParam("location") LocationParam location) {
        Preconditions.checkNotNull(player, "player");
        Preconditions.checkNotNull(location, "location");

        Optional<Planet> maybePlanet = planetService.findWithLocation(location.getValue());
        Planet planet = maybePlanet.orElseThrow(PlanetNotFoundWebException::new);

        if (!planet.isOwnedFrom(player)) {
            throw new NotYourPlanetWebException();
        }

        return PlanetDTO.fromPlanet(planet);
    }

    @Path("/{location}/building")
    public BuildingSubResource getBuildings() {
        return this.buildingSubResource;
    }
}
