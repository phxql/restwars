package restwars.rest.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class RestwarsConfiguration extends Configuration {
    @NotEmpty
    private String publicUrl;

    @Valid
    @NotNull
    @JsonProperty("database")
    private DataSourceFactory database;

    @Min(1)
    private int galaxies;

    @Min(1)
    private int solarSystems;

    @Min(1)
    private int planets;

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
}
