package restwars.rest.resources.param;

import restwars.model.planet.Location;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Param for JAXRS. Parses a string into a {@link restwars.model.planet.Location} object.
 */
public class LocationParam {
    private final Location location;

    public LocationParam(String value) {
        if (value == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        location = Location.parse(value);
    }

    public Location getValue() {
        return location;
    }
}
