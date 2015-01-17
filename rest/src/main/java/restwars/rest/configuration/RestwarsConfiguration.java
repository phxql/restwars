package restwars.rest.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * RESTwars configuration.
 */
public class RestwarsConfiguration extends Configuration {
    /**
     * Public URL of the server.
     */
    @NotEmpty
    private String publicUrl;

    /**
     * Database configuration.
     */
    @Valid
    @NotNull
    @JsonProperty("database")
    private DataSourceFactory database;

    /**
     * Password cache configuration.
     */
    @NotEmpty
    private String passwordCache;

    /**
     * Number of password iterations. More iterations = more secure password storage but also an increased CPU load when
     * checking a password.
     */
    @Min(1)
    private int passwordIterations;

    /**
     * Number of galaxies in the universe.
     */
    @Min(1)
    private int galaxies;

    /**
     * Number of solar systems in a galaxy.
     */
    @Min(1)
    private int solarSystems;

    /**
     * Number of planets in a solar system.
     */
    @Min(1)
    private int planets;

    /**
     * Round time in seconds.
     */
    @Min(1)
    private int roundTime;

    public DataSourceFactory getDatabase() {
        return database;
    }

    public void setDatabase(DataSourceFactory database) {
        this.database = database;
    }

    public String getPublicUrl() {
        if (publicUrl.endsWith("/")) {
            return publicUrl.substring(0, publicUrl.length() - 1);
        }

        return publicUrl;
    }

    public void setPublicUrl(String publicUrl) {
        this.publicUrl = publicUrl;
    }

    public int getGalaxies() {
        return galaxies;
    }

    public void setGalaxies(int galaxies) {
        this.galaxies = galaxies;
    }

    public int getSolarSystems() {
        return solarSystems;
    }

    public void setSolarSystems(int solarSystems) {
        this.solarSystems = solarSystems;
    }

    public int getPlanets() {
        return planets;
    }

    public void setPlanets(int planets) {
        this.planets = planets;
    }

    public int getRoundTime() {
        return roundTime;
    }

    public void setRoundTime(int roundTime) {
        this.roundTime = roundTime;
    }

    public int getPasswordIterations() {
        return passwordIterations;
    }

    public void setPasswordIterations(int passwordIterations) {
        this.passwordIterations = passwordIterations;
    }

    public String getPasswordCache() {
        return passwordCache;
    }

    public void setPasswordCache(String passwordCache) {
        this.passwordCache = passwordCache;
    }
}
