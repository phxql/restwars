package restwars.rest.api.building;

import org.hibernate.validator.constraints.NotEmpty;

public class CreateBuildingRequest {
    @NotEmpty
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
