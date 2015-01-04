package restwars.restapi.dto.player;

import com.google.common.base.Objects;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

@ApiModel(description = "Player registration")
public class RegisterPlayerRequest {
    @ApiModelProperty(value = "Username", required = true)
    @NotEmpty
    private String username;

    @ApiModelProperty(value = "Password", required = true)
    @NotEmpty
    private String password;

    public RegisterPlayerRequest() {
    }

    public RegisterPlayerRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("username", username)
                .add("password", password)
                .toString();
    }
}
