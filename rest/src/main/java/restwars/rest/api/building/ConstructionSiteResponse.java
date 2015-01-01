package restwars.rest.api.building;

import com.google.common.base.Preconditions;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import restwars.service.building.ConstructionSite;

@ApiModel(description = "Construction site")
public class ConstructionSiteResponse {
    @ApiModelProperty(value = "Building type", required = true)
    private final String type;

    @ApiModelProperty(value = "Level", required = true)
    private final int level;

    @ApiModelProperty(value = "Round started", required = true)
    private final long started;

    @ApiModelProperty(value = "Round done", required = true)
    private final long done;

    public ConstructionSiteResponse(String type, int level, long started, long done) {
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

    public static ConstructionSiteResponse fromConstructionSite(ConstructionSite constructionSite) {
        Preconditions.checkNotNull(constructionSite, "constructionSite");

        return new ConstructionSiteResponse(constructionSite.getType().toString(), constructionSite.getLevel(), constructionSite.getStarted(), constructionSite.getDone());
    }
}
