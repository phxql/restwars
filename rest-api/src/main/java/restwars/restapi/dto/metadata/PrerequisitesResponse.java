package restwars.restapi.dto.metadata;

import com.google.common.base.Objects;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import restwars.restapi.dto.building.BuildingResponse;
import restwars.restapi.dto.technology.TechnologyResponse;

import java.util.List;

@ApiModel(description = "Prerequisites")
public class PrerequisitesResponse {
    @ApiModelProperty(value = "Buildings", required = true)
    private List<BuildingResponse> buildings;
    @ApiModelProperty(value = "Technologies", required = true)
    private List<TechnologyResponse> technologies;

    public PrerequisitesResponse() {
    }

    public PrerequisitesResponse(List<BuildingResponse> buildings, List<TechnologyResponse> technologies) {
        this.buildings = buildings;
        this.technologies = technologies;
    }

    public List<BuildingResponse> getBuildings() {
        return buildings;
    }

    public void setBuildings(List<BuildingResponse> buildings) {
        this.buildings = buildings;
    }

    public List<TechnologyResponse> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(List<TechnologyResponse> technologies) {
        this.technologies = technologies;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("buildings", buildings)
                .add("technologies", technologies)
                .toString();
    }
}
