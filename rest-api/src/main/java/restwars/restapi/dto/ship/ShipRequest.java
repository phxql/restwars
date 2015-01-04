package restwars.restapi.dto.ship;

import com.google.common.base.Objects;
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

    public ShipRequest() {
    }

    public ShipRequest(String type, int amount) {
        this.type = type;
        this.amount = amount;
    }

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

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("type", type)
                .add("amount", amount)
                .toString();
    }
}
