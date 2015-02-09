package restwars.rest.resources;

import com.google.common.base.Preconditions;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import restwars.restapi.dto.GeneralInformationResponse;
import restwars.service.infrastructure.RoundService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/v1/")
@Api(value = "/v1", description = "General information")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RootResource {
    private final RoundService roundService;

    @Inject
    public RootResource(RoundService roundService) {
        this.roundService = Preconditions.checkNotNull(roundService, "roundService");
    }

    @GET
    @ApiOperation("Gets general information")
    public GeneralInformationResponse getGeneralInformation() {
        return new GeneralInformationResponse(roundService.getCurrentRound(), roundService.getCurrentRoundStarted());
    }
}
