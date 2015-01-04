package restwars.rest.mapper;

import com.google.common.base.Preconditions;
import restwars.restapi.dto.ship.ShipInConstructionResponse;
import restwars.service.ship.ShipInConstruction;

public final class ShipInConstructionMapper {
    private ShipInConstructionMapper() {
    }

    public static ShipInConstructionResponse fromShipInConstruction(ShipInConstruction shipInConstruction) {
        Preconditions.checkNotNull(shipInConstruction, "shipInConstruction");

        return new ShipInConstructionResponse(shipInConstruction.getType().toString(), shipInConstruction.getStarted(), shipInConstruction.getDone());
    }
}
