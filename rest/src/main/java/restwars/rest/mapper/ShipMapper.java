package restwars.rest.mapper;

import com.google.common.base.Preconditions;
import restwars.restapi.dto.metadata.ShipMetadataResponse;
import restwars.restapi.dto.ship.ShipRequest;
import restwars.restapi.dto.ship.ShipResponse;
import restwars.service.ship.Ship;
import restwars.service.ship.ShipType;
import restwars.service.ship.Ships;

import java.util.List;
import java.util.stream.Collectors;

public final class ShipMapper {
    private ShipMapper() {
    }

    public static ShipResponse fromShip(Ship ship) {
        Preconditions.checkNotNull(ship, "ship");

        return new ShipResponse(ship.getType().toString(), ship.getAmount());
    }

    public static ShipMetadataResponse fromShipType(ShipType shipType) {
        Preconditions.checkNotNull(shipType, "shipType");

        return new ShipMetadataResponse(
                shipType.name(), shipType.getBuildTime(), ResourcesMapper.fromResources(shipType.getBuildCost()),
                shipType.getSpeed(), shipType.getAttackPoints(), shipType.getDefensePoints(), shipType.getStorageCapacity()
        );
    }

    public static Ships fromShips(List<ShipRequest> ships) {
        return new Ships(ships.stream()
                .map(s -> new Ship(ShipType.valueOf(s.getType()), s.getAmount()))
                .collect(Collectors.toList())
        );
    }
}
