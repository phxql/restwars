package restwars.rest.configuration;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class DatabaseConfiguration {
    @NotEmpty
    private String driverClass;
    @NotEmpty
    private String url;
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotEmpty
    private String sqlDialect;

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getSqlDialect() {
        return sqlDialect;
    }

    public void setSqlDialect(String sqlDialect) {
        this.sqlDialect = sqlDialect;
    }
}
