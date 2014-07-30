package restwars.rest.api.technology;

import com.google.common.base.Preconditions;
import restwars.service.technology.Technology;

public class TechnologyResponse {
    private final String type;

    private final int level;

    public TechnologyResponse(String type, int level) {
        this.type = Preconditions.checkNotNull(type, "type");
        this.level = level;
    }

    public String getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }

    public static TechnologyResponse fromTechnology(Technology technology) {
        Preconditions.checkNotNull(technology, "technology");

        return new TechnologyResponse(technology.getType().toString(), technology.getLevel());
    }
}
