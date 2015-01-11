package restwars.rest.resources;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.joda.time.DateTime;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Path("/v1/system")
@Api(value = "/v1/system", description = "System management")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SystemResource {
    private static final String GIT_REVISION_KEY = "git-revision";
    private static final String PROPERTIES_FILE = "/build.properties";
    private final String gitRevision;

    @Inject
    public SystemResource() {
        try (InputStream inputStream = SystemResource.class.getResourceAsStream(PROPERTIES_FILE)) {
            Properties properties = new Properties();
            properties.load(inputStream);

            gitRevision = properties.getProperty(GIT_REVISION_KEY);
        } catch (IOException e) {
            throw new IllegalStateException("IOException while opening properties file", e);
        }
    }

    @ApiOperation(value = "Pings the server", notes = "The server responds with a pong and the current time in ISO 8601 format")
    @GET
    @Path("/ping")
    @Produces(MediaType.TEXT_PLAIN)
    public String ping() {
        return "pong " + DateTime.now();
    }

    @ApiOperation(value = "Gets the server version")
    @GET
    @Path("/version")
    @Produces(MediaType.TEXT_PLAIN)
    public String version() {
        return gitRevision;
    }
}
