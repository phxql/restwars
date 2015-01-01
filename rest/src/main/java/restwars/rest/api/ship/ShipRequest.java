package restwars.rest.api.ship;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;

@ApiModel(description = "Ships")
public class ShipRequest {
    @NotEmpty
    @ApiModelProperty(value = "Ship type", required = true)
    private String type;

    @Min(1)
    @ApiModelProperty(value = "Amount", required = true)
    private int amount;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
