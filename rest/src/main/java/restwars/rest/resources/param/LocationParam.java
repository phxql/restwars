package restwars.rest.resources.param;

import restwars.service.planet.Location;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

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
