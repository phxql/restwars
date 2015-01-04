package restwars.restapi.dto.ship;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Ships")
public class ShipResponse {
    @ApiModelProperty(value = "Ship type", required = true)
    private String type;

    @ApiModelProperty(value = "Amount", required = true)
    private long amount;

    public ShipResponse() {
    }

    public ShipResponse(String type, long amount) {
        this.type = Preconditions.checkNotNull(type, "type");
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public long getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("type", type)
                .add("amount", amount)
                .toString();
    }
}
