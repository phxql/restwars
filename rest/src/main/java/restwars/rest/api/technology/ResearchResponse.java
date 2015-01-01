package restwars.rest.api.technology;

import com.google.common.base.Preconditions;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import restwars.service.technology.Research;

@ApiModel(description = "Research")
public class ResearchResponse {
    @ApiModelProperty(value = "Technology type", required = true)
    private final String type;

    @ApiModelProperty(value = "Level", required = true)
    private final int level;

    @ApiModelProperty(value = "Round started", required = true)
    private final long started;

    @ApiModelProperty(value = "Round done", required = true)
    private final long done;

    public ResearchResponse(String type, int level, long started, long done) {
        this.type = Preconditions.checkNotNull(type, "type");
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

    public static ResearchResponse fromResearch(Research research) {
        Preconditions.checkNotNull(research, "research");

        return new ResearchResponse(research.getType().toString(), research.getLevel(), research.getStarted(), research.getDone());
    }

}
