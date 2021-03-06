package restwars.service.mechanics.impl;

import com.google.common.base.Preconditions;
import restwars.model.building.BuildingType;
import restwars.model.resource.Resources;
import restwars.model.ship.ShipType;
import restwars.model.technology.TechnologyType;
import restwars.model.techtree.Prerequisites;
import restwars.service.mechanics.ResourcesMechanics;
import restwars.service.mechanics.ShipMechanics;

import javax.inject.Inject;

public class ShipMechanicsImpl implements ShipMechanics {
    private final ResourcesMechanics resourcesMechanics;

    @Inject
    public ShipMechanicsImpl(ResourcesMechanics resourcesMechanics) {
        this.resourcesMechanics = Preconditions.checkNotNull(resourcesMechanics, "resourcesMechanics");
    }

    @Override
    public Resources getBuildCost(ShipType type) {
        Preconditions.checkNotNull(type, "type");

        switch (type) {
            case MOSQUITO:
                return new Resources(100, 20, 270);
            case DAGGER:
                return new Resources(120, 35, 465);
            case DAEDALUS:
                return new Resources(250, 100, 1320);
            case COLONY:
                return new Resources(350, 150, 1750);
            case PROBE:
                return new Resources(8, 2, 27);
            case MULE:
                return new Resources(200, 100, 1225);
            default:
                throw new IllegalArgumentException("Unknown ship " + type);
        }
    }

    @Override
    public int getBuildTime(ShipType type) {
        Preconditions.checkNotNull(type, "type");

        switch (type) {
            case MOSQUITO:
                return 10;
            case DAGGER:
                return 15;
            case DAEDALUS:
                return 30;
            case COLONY:
                return 60;
            case PROBE:
                return 2;
            case MULE:
                return 20;
            default:
                throw new IllegalArgumentException("Unknown ship " + type);
        }
    }

    @Override
    public double getFlightCostModifier(ShipType type) {
        Preconditions.checkNotNull(type, "type");

        switch (type) {
            case MOSQUITO:
                return 1;
            case DAGGER:
                return 1.5;
            case DAEDALUS:
                return 2;
            case COLONY:
                return 2;
            case PROBE:
                return 0.5;
            case MULE:
                return 1.5;
            default:
                throw new IllegalArgumentException("Unknown ship " + type);
        }
    }

    @Override
    public double getFlightSpeed(ShipType type) {
        Preconditions.checkNotNull(type, "type");

        switch (type) {
            case MOSQUITO:
                return 1;
            case DAGGER:
                return 2;
            case DAEDALUS:
                return 1;
            case COLONY:
                return 0.5;
            case PROBE:
                return 2;
            case MULE:
                return 1;
            default:
                throw new IllegalArgumentException("Unknown ship " + type);
        }
    }

    @Override
    public int getAttackPoints(ShipType type) {
        Preconditions.checkNotNull(type, "type");

        switch (type) {
            case MOSQUITO:
                return 14;
            case DAGGER:
                return 20;
            case DAEDALUS:
                return 50;
            case COLONY:
                return 0;
            case PROBE:
                return 0;
            case MULE:
                return 0;
            default:
                throw new IllegalArgumentException("Unknown ship " + type);
        }
    }

    @Override
    public int getDefensePoints(ShipType type) {
        Preconditions.checkNotNull(type, "type");

        switch (type) {
            case MOSQUITO:
                return 10;
            case DAGGER:
                return 15;
            case DAEDALUS:
                return 35;
            case COLONY:
                return 75;
            case PROBE:
                return 1;
            case MULE:
                return 20;
            default:
                throw new IllegalArgumentException("Unknown ship " + type);
        }
    }

    @Override
    public int getCargoSpace(ShipType type) {
        Preconditions.checkNotNull(type, "type");

        switch (type) {
            case MOSQUITO:
                return 10;
            case DAGGER:
                return 25;
            case DAEDALUS:
                return 100;
            case COLONY:
                return 500;
            case PROBE:
                return 0;
            case MULE:
                return 750;
            default:
                throw new IllegalArgumentException("Unknown ship " + type);
        }
    }

    @Override
    public Prerequisites getPrerequisites(ShipType type) {
        Preconditions.checkNotNull(type, "type");

        switch (type) {
            case MOSQUITO:
                return Prerequisites.building(BuildingType.SHIPYARD, 1);
            case DAGGER:
                return new Prerequisites(
                        Prerequisites.building(BuildingType.SHIPYARD, 1),
                        Prerequisites.technology(TechnologyType.COMBUSTION_ENGINE, 2)
                );
            case DAEDALUS:
                return new Prerequisites(
                        Prerequisites.building(BuildingType.SHIPYARD, 2),
                        Prerequisites.technology(TechnologyType.COMBUSTION_ENGINE, 3)
                );
            case COLONY:
                return new Prerequisites(
                        Prerequisites.building(BuildingType.SHIPYARD, 2),
                        Prerequisites.technology(TechnologyType.COMBUSTION_ENGINE, 1)
                );
            case PROBE:
                return Prerequisites.building(BuildingType.SHIPYARD, 1);
            case MULE:
                return new Prerequisites(
                        Prerequisites.building(BuildingType.SHIPYARD, 1),
                        Prerequisites.technology(TechnologyType.COMBUSTION_ENGINE, 1)
                );
            default:
                throw new IllegalArgumentException("Unknown ship " + type);
        }
    }

    @Override
    public long calculatePointsForShipInConstruction(ShipType type) {
        return calculatePointsForShip(type);
    }

    @Override
    public long calculatePointsForShip(ShipType type) {
        Preconditions.checkNotNull(type, "type");

        return resourcesMechanics.calculatePointsForResources(getBuildCost(type));
    }
}
