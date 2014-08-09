package restwars.rest.configuration;

import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;

public class RestwarsConfiguration extends Configuration {
    @NotEmpty
    private String serverName;

    @Valid
    private DatabaseConfiguration database;

    public DatabaseConfiguration getDatabase() {
        return database;
    }

    public void setDatabase(DatabaseConfiguration database) {
        this.database = database;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }
}
