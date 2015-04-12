package restwars.rest.mapper;

import com.google.common.base.Preconditions;
import restwars.model.points.Points;
import restwars.restapi.dto.player.PointResponse;

public final class PointsMapper {
    private PointsMapper() {
    }

    public static PointResponse fromPoints(Points points) {
        Preconditions.checkNotNull(points, "points");

        return new PointResponse(points.getPoints(), points.getRound());
    }
}
