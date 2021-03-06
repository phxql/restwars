package restwars.rest.mapper;

import com.google.common.base.Preconditions;
import restwars.model.ship.Ship;
import restwars.model.ship.ShipType;
import restwars.model.ship.Ships;
import restwars.restapi.dto.metadata.ShipMetadataResponse;
import restwars.restapi.dto.ship.ShipRequest;
import restwars.restapi.dto.ship.ShipResponse;
import restwars.service.mechanics.ShipMechanics;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Maps ships objects to DTOs and vice versa.
 */
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

    public static ShipMetadataResponse fromShipType(ShipType shipType, ShipMechanics shipMechanics) {
        Preconditions.checkNotNull(shipType, "shipType");

        return new ShipMetadataResponse(
                shipType.name(), shipMechanics.getBuildTime(shipType), ResourcesMapper.fromResources(shipMechanics.getBuildCost(shipType)),
                shipMechanics.getFlightSpeed(shipType), shipMechanics.getAttackPoints(shipType), shipMechanics.getDefensePoints(shipType),
                shipMechanics.getCargoSpace(shipType), shipType.getDescription(),
                PrerequisitesMapper.fromPrerequisites(shipMechanics.getPrerequisites(shipType))
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
