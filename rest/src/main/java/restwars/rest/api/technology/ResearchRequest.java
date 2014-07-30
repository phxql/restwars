package restwars.rest.api.technology;

import org.hibernate.validator.constraints.NotEmpty;

public class ResearchRequest {

    @NotEmpty
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
