package restwars.rest.mapper;

import com.google.common.base.Preconditions;
import restwars.model.ship.ShipInConstruction;
import restwars.restapi.dto.ship.ShipInConstructionResponse;

/**
 * Maps ship in construction entities to DTOs and vice versa.
 */
public final class ShipInConstructionMapper {
    private ShipInConstructionMapper() {
    }

    public static ShipInConstructionResponse fromShipInConstruction(ShipInConstruction shipInConstruction) {
        Preconditions.checkNotNull(shipInConstruction, "shipInConstruction");

        return new ShipInConstructionResponse(shipInConstruction.getType().toString(), shipInConstruction.getStarted(), shipInConstruction.getDone());
    }
}
