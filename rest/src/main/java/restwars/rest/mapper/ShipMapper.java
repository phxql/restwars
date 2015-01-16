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

    public static List<ShipResponse> fromShips(Ships ships) {
        Preconditions.checkNotNull(ships, "ships");

        return ships.stream().map(ShipMapper::fromShip).collect(Collectors.toList());
    }

    public static ShipMetadataResponse fromShipType(ShipType shipType) {
        Preconditions.checkNotNull(shipType, "shipType");

        return new ShipMetadataResponse(
                shipType.name(), shipType.getBuildTime(), ResourcesMapper.fromResources(shipType.getBuildCost()),
                shipType.getSpeed(), shipType.getAttackPoints(), shipType.getDefensePoints(), shipType.getStorageCapacity()
        );
    }

    /**
     * Converts a list of ShipRequests into a Ships object.
     * <p/>
     * This method won't include ships which have an amount of 0 or less.
     *
     * @param ships List of ShipRequests.
     * @return Ships object.
     */
    public static Ships fromShips(List<ShipRequest> ships) {
        Preconditions.checkNotNull(ships, "ships");

        return new Ships(ships.stream()
                .filter(s -> s.getAmount() > 0)
                .map(s -> new Ship(ShipType.valueOf(s.getType()), s.getAmount()))
                .collect(Collectors.toList())
        );
    }
}
