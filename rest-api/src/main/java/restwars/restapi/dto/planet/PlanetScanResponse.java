package restwars.restapi.dto.planet;

import com.google.common.base.Objects;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(description = "A planet scan")
public class PlanetScanResponse {
    @ApiModelProperty(value = "Location", required = true)
    private String location;

    @ApiModelProperty(value = "Owner", required = true)
    private String owner;

    public PlanetScanResponse() {
    }

    public PlanetScanResponse(String location, String owner) {
        this.location = location;
        this.owner = owner;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }


    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("location", location)
                .add("owner", owner)
                .toString();
    }
}
