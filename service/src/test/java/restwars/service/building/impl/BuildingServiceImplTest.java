package restwars.service.building.impl;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import restwars.model.building.Building;
import restwars.model.building.BuildingType;
import restwars.model.building.Buildings;
import restwars.model.building.ConstructionSite;
import restwars.model.resource.Resources;
import restwars.model.technology.Technologies;
import restwars.model.techtree.Prerequisites;
import restwars.service.Data;
import restwars.service.building.BuildingDAO;
import restwars.service.building.BuildingException;
import restwars.service.building.ConstructionSiteDAO;
import restwars.service.event.EventService;
import restwars.service.infrastructure.RoundService;
import restwars.service.infrastructure.UUIDFactory;
import restwars.service.mechanics.BuildingMechanics;
import restwars.service.mechanics.TechnologyMechanics;
import restwars.service.planet.PlanetDAO;
import restwars.service.technology.TechnologyDAO;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.testng.Assert.fail;

public class BuildingServiceImplTest {

    private BuildingServiceImpl sut;
    private UUIDFactory uuidFactory;
    private BuildingDAO buildingDAO;
    private RoundService roundService;
    private ConstructionSiteDAO constructionSiteDAO;
    private PlanetDAO planetDAO;
    private EventService eventService;
    private TechnologyDAO technologyDAO;
    private BuildingMechanics buildingMechanics;
    private TechnologyMechanics technologyMechanics;

    @BeforeMethod
    public void setUp() throws Exception {
        uuidFactory = mock(UUIDFactory.class);
        buildingDAO = mock(BuildingDAO.class);
        roundService = mock(RoundService.class);
        constructionSiteDAO = mock(ConstructionSiteDAO.class);
        planetDAO = mock(PlanetDAO.class);
        eventService = mock(EventService.class);
        technologyDAO = mock(TechnologyDAO.class);
        buildingMechanics = mock(BuildingMechanics.class);
        technologyMechanics = mock(TechnologyMechanics.class);

        sut = new BuildingServiceImpl(uuidFactory, buildingDAO, roundService, constructionSiteDAO, planetDAO, eventService, technologyDAO, buildingMechanics, technologyMechanics);
    }

    @Test
    public void testFindBuildingsOnPlanet() throws Exception {
        sut.findBuildingsOnPlanet(Data.Player1.Planet1.PLANET);

        verify(buildingDAO).findWithPlanetId(Data.Player1.Planet1.PLANET.getId());
    }

    @Test
    public void testFindConstructionSitesOnPlanet() throws Exception {
        sut.findConstructionSitesOnPlanet(Data.Player1.Planet1.PLANET);

        verify(constructionSiteDAO).findWithPlanetId(Data.Player1.Planet1.PLANET.getId());
    }

    @Test
    public void testManifestBuilding() throws Exception {
        UUID id = UUID.randomUUID();
        when(uuidFactory.create()).thenReturn(id);

        sut.manifestBuilding(Data.Player1.Planet1.PLANET, BuildingType.COMMAND_CENTER, 10);

        verify(buildingDAO).insert(new Building(id, BuildingType.COMMAND_CENTER, 10, Data.Player1.Planet1.PLANET.getId()));
    }

    @Test
    public void testConstructBuilding() throws Exception {
        when(buildingMechanics.getPrerequisites(BuildingType.COMMAND_CENTER)).thenReturn(Prerequisites.NONE);
        when(technologyDAO.findAllWithPlayerId(Data.Player1.Planet1.PLANET.getOwnerId())).thenReturn(Technologies.NONE);
        when(buildingDAO.findWithPlanetId(Data.Player1.Planet1.PLANET.getId())).thenReturn(new Buildings(Data.Player1.Planet1.COMMAND_CENTER));

        when(constructionSiteDAO.findWithPlanetId(Data.Player1.Planet1.PLANET.getId())).thenReturn(Collections.emptyList());

        when(buildingMechanics.calculateBuildCost(BuildingType.COMMAND_CENTER, 1)).thenReturn(new Resources(10, 20, 30));
        when(technologyMechanics.calculateBuildCostReduction(0)).thenReturn(0.0);

        when(buildingMechanics.calculateBuildTime(BuildingType.COMMAND_CENTER, 1)).thenReturn(1);

        UUID id = UUID.randomUUID();
        when(uuidFactory.create()).thenReturn(id);
        when(roundService.getCurrentRound()).thenReturn(1L);

        ConstructionSite expected = new ConstructionSite(id, BuildingType.COMMAND_CENTER, 1, Data.Player1.Planet1.PLANET.getId(), Data.Player1.PLAYER.getId(), 1, 2);
        ConstructionSite actual = sut.constructBuilding(Data.Player1.Planet1.PLANET, BuildingType.COMMAND_CENTER);

        verify(planetDAO).update(Data.Player1.Planet1.PLANET.withResources(Resources.NONE));
        verify(constructionSiteDAO).insert(expected);

        assertThat(expected, is(actual));
    }

    @Test(description = "Prerequisites not fulfilled")
    public void testConstructBuilding2() throws Exception {
        when(buildingMechanics.getPrerequisites(BuildingType.COMMAND_CENTER)).thenReturn(Prerequisites.buildings(new Prerequisites.Building(BuildingType.SHIPYARD, 10)));
        when(technologyDAO.findAllWithPlayerId(Data.Player1.Planet1.PLANET.getOwnerId())).thenReturn(Technologies.NONE);
        when(buildingDAO.findWithPlanetId(Data.Player1.Planet1.PLANET.getId())).thenReturn(new Buildings(Data.Player1.Planet1.COMMAND_CENTER));

        try {
            sut.constructBuilding(Data.Player1.Planet1.PLANET, BuildingType.COMMAND_CENTER);
            fail("Expected exception");
        } catch (BuildingException e) {
            assertThat(e.getReason(), is(BuildingException.Reason.PREREQUISITES_NOT_FULFILLED));
        }
    }

    @Test(description = "Not enough build queues")
    public void testConstructBuilding3() throws Exception {
        when(buildingMechanics.getPrerequisites(BuildingType.COMMAND_CENTER)).thenReturn(Prerequisites.NONE);
        when(technologyDAO.findAllWithPlayerId(Data.Player1.Planet1.PLANET.getOwnerId())).thenReturn(Technologies.NONE);
        when(buildingDAO.findWithPlanetId(Data.Player1.Planet1.PLANET.getId())).thenReturn(new Buildings(Data.Player1.Planet1.COMMAND_CENTER));

        when(constructionSiteDAO.findWithPlanetId(Data.Player1.Planet1.PLANET.getId())).thenReturn(Arrays.asList(Data.Player1.Planet1.CONSTRUCTION_SITE));
        try {
            sut.constructBuilding(Data.Player1.Planet1.PLANET, BuildingType.COMMAND_CENTER);
            fail("Expected exception");
        } catch (BuildingException e) {
            assertThat(e.getReason(), is(BuildingException.Reason.NOT_ENOUGH_BUILD_QUEUES));
        }
    }

    @Test(description = "Insufficient resoyrces")
    public void testConstructBuilding4() throws Exception {
        when(buildingMechanics.getPrerequisites(BuildingType.COMMAND_CENTER)).thenReturn(Prerequisites.NONE);
        when(technologyDAO.findAllWithPlayerId(Data.Player1.Planet1.PLANET.getOwnerId())).thenReturn(Technologies.NONE);
        when(buildingDAO.findWithPlanetId(Data.Player1.Planet1.PLANET.getId())).thenReturn(new Buildings(Data.Player1.Planet1.COMMAND_CENTER));
        when(constructionSiteDAO.findWithPlanetId(Data.Player1.Planet1.PLANET.getId())).thenReturn(Collections.emptyList());

        when(buildingMechanics.calculateBuildCost(BuildingType.COMMAND_CENTER, 1)).thenReturn(new Resources(1000, 2000, 3000));
        try {
            sut.constructBuilding(Data.Player1.Planet1.PLANET, BuildingType.COMMAND_CENTER);
            fail("Expected exception");
        } catch (BuildingException e) {
            assertThat(e.getReason(), is(BuildingException.Reason.INSUFFICIENT_RESOURCES));
        }

    }

    @Test
    public void testUpgradeBuilding() throws Exception {

    }

    @Test
    public void testConstructOrUpgradeBuilding() throws Exception {

    }

    @Test
    public void testCalculateBuildCostWithoutBonuses() throws Exception {

    }

    @Test
    public void testFinishConstructionSites() throws Exception {

    }

    @Test
    public void testCalculateBuildTime() throws Exception {

    }

    @Test
    public void testCalculateBuildTimeWithoutBonuses() throws Exception {

    }

    @Test
    public void testCalculateBuildCost() throws Exception {

    }
}