package restwars.service.techtree;

import org.testng.annotations.Test;
import restwars.service.building.BuildingType;
import restwars.service.technology.TechnologyType;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PrerequisitesTest {
    @Test
    public void testOneBuilding() throws Exception {
        Prerequisites sut = Prerequisites.building(BuildingType.COMMAND_CENTER, 2);

        assertThat(sut.fulfilled(Arrays.asList(new Prerequisites.Building(BuildingType.COMMAND_CENTER, 1)), Collections.emptyList()), is(false));
        assertThat(sut.fulfilled(Arrays.asList(new Prerequisites.Building(BuildingType.COMMAND_CENTER, 2)), Collections.emptyList()), is(true));
        assertThat(sut.fulfilled(Arrays.asList(new Prerequisites.Building(BuildingType.COMMAND_CENTER, 3)), Collections.emptyList()), is(true));
    }

    @Test
    public void testMultipleBuildings() throws Exception {
        Prerequisites sut = new Prerequisites(Arrays.asList(new Prerequisites.Building(BuildingType.COMMAND_CENTER, 2), new Prerequisites.Building(BuildingType.CRYSTAL_MINE, 2)), Collections.emptyList());

        assertThat(sut.fulfilled(Arrays.asList(new Prerequisites.Building(BuildingType.COMMAND_CENTER, 2)), Collections.emptyList()), is(false));
        assertThat(sut.fulfilled(Arrays.asList(new Prerequisites.Building(BuildingType.CRYSTAL_MINE, 2)), Collections.emptyList()), is(false));
        assertThat(sut.fulfilled(Arrays.asList(new Prerequisites.Building(BuildingType.COMMAND_CENTER, 1), new Prerequisites.Building(BuildingType.CRYSTAL_MINE, 1)), Collections.emptyList()), is(false));
        assertThat(sut.fulfilled(Arrays.asList(new Prerequisites.Building(BuildingType.COMMAND_CENTER, 1), new Prerequisites.Building(BuildingType.CRYSTAL_MINE, 2)), Collections.emptyList()), is(false));
        assertThat(sut.fulfilled(Arrays.asList(new Prerequisites.Building(BuildingType.COMMAND_CENTER, 2), new Prerequisites.Building(BuildingType.CRYSTAL_MINE, 1)), Collections.emptyList()), is(false));
        assertThat(sut.fulfilled(Arrays.asList(new Prerequisites.Building(BuildingType.COMMAND_CENTER, 2), new Prerequisites.Building(BuildingType.CRYSTAL_MINE, 2)), Collections.emptyList()), is(true));
    }

    @Test
    public void testOneTechnology() throws Exception {
        Prerequisites sut = Prerequisites.technology(TechnologyType.BUILDING_BUILD_COST_REDUCTION, 2);

        assertThat(sut.fulfilled(Collections.emptyList(), Arrays.asList(new Prerequisites.Technology(TechnologyType.BUILDING_BUILD_COST_REDUCTION, 1))), is(false));
        assertThat(sut.fulfilled(Collections.emptyList(), Arrays.asList(new Prerequisites.Technology(TechnologyType.BUILDING_BUILD_COST_REDUCTION, 2))), is(true));
        assertThat(sut.fulfilled(Collections.emptyList(), Arrays.asList(new Prerequisites.Technology(TechnologyType.BUILDING_BUILD_COST_REDUCTION, 3))), is(true));
    }

    @Test
    public void testMultipleTechnologies() throws Exception {
// TODO: Uncomment if there is more than one technology!

//        Prerequisites sut = new Prerequisites(Collections.emptyList(), Arrays.asList(new Prerequisites.Technology(TechnologyType.CRYSTAL_MINE_EFFICIENCY, 2), new Prerequisites.Technology(TechnologyType.GAS_REFINERY_EFFICIENCY, 2)));
//
//        assertThat(sut.fulfilled(Collections.emptyList(), Arrays.asList(new Prerequisites.Technology(TechnologyType.CRYSTAL_MINE_EFFICIENCY, 2))), is(false));
//        assertThat(sut.fulfilled(Collections.emptyList(), Arrays.asList(new Prerequisites.Technology(TechnologyType.GAS_REFINERY_EFFICIENCY, 2))), is(false));
//        assertThat(sut.fulfilled(Collections.emptyList(), Arrays.asList(new Prerequisites.Technology(TechnologyType.CRYSTAL_MINE_EFFICIENCY, 1), new Prerequisites.Technology(TechnologyType.GAS_REFINERY_EFFICIENCY, 1))), is(false));
//        assertThat(sut.fulfilled(Collections.emptyList(), Arrays.asList(new Prerequisites.Technology(TechnologyType.CRYSTAL_MINE_EFFICIENCY, 1), new Prerequisites.Technology(TechnologyType.GAS_REFINERY_EFFICIENCY, 2))), is(false));
//        assertThat(sut.fulfilled(Collections.emptyList(), Arrays.asList(new Prerequisites.Technology(TechnologyType.CRYSTAL_MINE_EFFICIENCY, 2), new Prerequisites.Technology(TechnologyType.GAS_REFINERY_EFFICIENCY, 1))), is(false));
//        assertThat(sut.fulfilled(Collections.emptyList(), Arrays.asList(new Prerequisites.Technology(TechnologyType.CRYSTAL_MINE_EFFICIENCY, 2), new Prerequisites.Technology(TechnologyType.GAS_REFINERY_EFFICIENCY, 2))), is(true));
    }
}