package restwars.rest.api.building;

import com.google.common.base.Preconditions;
import restwars.service.building.ConstructionSite;

public class ConstructionSiteDTO {
    private final String type;

    private final int level;

    private final long started;

    private final long done;

    public ConstructionSiteDTO(String type, int level, long started, long done) {
        this.type = type;
        this.level = level;
        this.started = started;
        this.done = done;
    }

    public String getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }

    public long getStarted() {
        return started;
    }

    public long getDone() {
        return done;
    }

    public static ConstructionSiteDTO fromConstructionSite(ConstructionSite constructionSite) {
        Preconditions.checkNotNull(constructionSite, "constructionSite");

        return new ConstructionSiteDTO(constructionSite.getType().toString(), constructionSite.getLevel(), constructionSite.getStarted(), constructionSite.getDone());
    }
}
