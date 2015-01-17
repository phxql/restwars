package restwars.rest.mapper;

import restwars.restapi.dto.ResourcesResponse;
import restwars.service.resource.Resources;

/**
 * Maps resources objects to DTOs and vice versa.
 */
public final class ResourcesMapper {
    private ResourcesMapper() {
    }

    public static ResourcesResponse fromResources(Resources resources) {
        return new ResourcesResponse(resources.getCrystals(), resources.getGas(), resources.getEnergy());
    }
}
