package restwars.storage.mapper;

import org.jooq.Record;
import restwars.service.building.Building;
import restwars.service.building.BuildingType;

import static restwars.storage.jooq.Tables.BUILDING;

public final class BuildingMapper {
    private BuildingMapper() {
    }

    public static Building fromRecord(Record record) {
        return new Building(
                record.getValue(BUILDING.ID),
                BuildingType.fromId(record.getValue(BUILDING.TYPE)),
                record.getValue(BUILDING.LEVEL),
                record.getValue(BUILDING.PLANET_ID)
        );
    }
}
