package restwars.rest;

import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

public class RestwarsConfiguration extends Configuration {
    @NotEmpty
    private String serverName;

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }
}
