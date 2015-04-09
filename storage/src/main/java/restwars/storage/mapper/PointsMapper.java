package restwars.storage.mapper;

import org.jooq.Record;
import restwars.model.points.Points;

import static restwars.storage.jooq.Tables.POINTS;

public final class PointsMapper {
    private PointsMapper() {
    }

    public static Points fromRecord(Record record) {
        return new Points(
                record.getValue(POINTS.ID),
                record.getValue(POINTS.PLAYER_ID),
                record.getValue(POINTS.ROUND),
                record.getValue(POINTS.POINTS_)
        );
    }
}
