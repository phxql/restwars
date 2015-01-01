package restwars.rest.api.ship;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;
import restwars.service.ship.ShipType;

@ApiModel(description = "Builds a ship")
public class BuildShipRequest {
    @NotEmpty
    @ApiModelProperty(value = "Ship type", required = true)
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @ApiModelProperty(hidden = true)
    public ShipType getParsedType() {
        return ShipType.valueOf(type);
    }
}
