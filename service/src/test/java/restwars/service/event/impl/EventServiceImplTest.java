package restwars.service.event.impl;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import restwars.model.event.Event;
import restwars.model.event.EventType;
import restwars.service.Data;
import restwars.service.event.EventDAO;
import restwars.service.event.EventWithPlanet;
import restwars.service.infrastructure.RoundService;
import restwars.service.infrastructure.UUIDFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

public class EventServiceImplTest {

    private EventServiceImpl sut;
    private EventDAO eventDAO;
    private UUIDFactory uuidFactory;
    private RoundService roundService;

    @BeforeMethod
    public void setUp() throws Exception {
        eventDAO = mock(EventDAO.class);
        uuidFactory = mock(UUIDFactory.class);
        roundService = mock(RoundService.class);

        when(roundService.getCurrentRound()).thenReturn(1L);

        sut = new EventServiceImpl(eventDAO, uuidFactory, roundService);
    }

    @Test
    public void testFindSince() throws Exception {
        UUID playerId = UUID.randomUUID();

        Event event = new Event(UUID.randomUUID(), Data.Player1.PLAYER.getId(), Data.Player1.Planet1.PLANET.getId(), EventType.BUILDING_COMPLETED, 1, Optional.empty());

        List<EventWithPlanet> expected = Arrays.asList(new EventWithPlanet(event, Data.Player1.Planet1.PLANET));
        when(eventDAO.findSince(playerId, 1)).thenReturn(expected);

        List<EventWithPlanet> actual = sut.findSince(playerId, 1);
        assertThat(actual, is(expected));
    }

    @Test
    public void testCreateBuildingCompletedEvent() throws Exception {
        UUID id = UUID.randomUUID();
        when(uuidFactory.create()).thenReturn(id);

        sut.createBuildingCompletedEvent(Data.Player1.PLAYER.getId(), Data.Player1.Planet1.PLANET.getId());

        verify(eventDAO).insert(new Event(id, Data.Player1.PLAYER.getId(), Data.Player1.Planet1.PLANET.getId(), EventType.BUILDING_COMPLETED, 1, Optional.empty()));
    }

    @Test
    public void testCreateFightHappenedEvent() throws Exception {
        UUID fightId = UUID.randomUUID();

        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        when(uuidFactory.create()).thenReturn(id1, id2);

        sut.createFightHappenedEvent(Data.Player1.PLAYER.getId(), Data.Player2.PLAYER.getId(), Data.Player1.Planet1.PLANET.getId(), fightId);

        verify(eventDAO).insert(new Event(id1, Data.Player1.PLAYER.getId(), Data.Player1.Planet1.PLANET.getId(), EventType.FIGHT_HAPPENED, 1, Optional.of(fightId)));
        verify(eventDAO).insert(new Event(id2, Data.Player2.PLAYER.getId(), Data.Player1.Planet1.PLANET.getId(), EventType.FIGHT_HAPPENED, 1, Optional.of(fightId)));
    }

    @Test
    public void testCreateFlightDetectedEvent() throws Exception {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        when(uuidFactory.create()).thenReturn(id1, id2);

        UUID flightId = UUID.randomUUID();
        UUID detectedFlightId = UUID.randomUUID();

        sut.createFlightDetectedEvent(Data.Player1.PLAYER.getId(), Data.Player2.PLAYER.getId(), Data.Player1.Planet1.PLANET.getId(), flightId, detectedFlightId);

        verify(eventDAO).insert(new Event(id1, Data.Player1.PLAYER.getId(), Data.Player1.Planet1.PLANET.getId(), EventType.FLIGHT_HAS_BEEN_DETECTED, 1, Optional.empty()));
        verify(eventDAO).insert(new Event(id2, Data.Player2.PLAYER.getId(), Data.Player1.Planet1.PLANET.getId(), EventType.FLIGHT_DETECTED, 1, Optional.empty()));
    }

    @Test
    public void testCreateTransportArrivedEvent() throws Exception {
        UUID id = UUID.randomUUID();
        when(uuidFactory.create()).thenReturn(id);

        sut.createTransportArrivedEvent(Data.Player1.PLAYER.getId(), Data.Player1.Planet1.PLANET.getId());

        verify(eventDAO).insert(new Event(id, Data.Player1.PLAYER.getId(), Data.Player1.Planet1.PLANET.getId(), EventType.TRANSPORT_ARRIVED, 1, Optional.empty()));
    }

    @Test
    public void testCreatePlanetColonizedEvent() throws Exception {
        UUID id = UUID.randomUUID();
        when(uuidFactory.create()).thenReturn(id);

        sut.createPlanetColonizedEvent(Data.Player1.PLAYER.getId(), Data.Player1.Planet1.PLANET.getId());

        verify(eventDAO).insert(new Event(id, Data.Player1.PLAYER.getId(), Data.Player1.Planet1.PLANET.getId(), EventType.PLANET_COLONIZED, 1, Optional.empty()));
    }

    @Test
    public void testCreateShipCompletedEvent() throws Exception {
        UUID id = UUID.randomUUID();
        when(uuidFactory.create()).thenReturn(id);

        sut.createShipCompletedEvent(Data.Player1.PLAYER.getId(), Data.Player1.Planet1.PLANET.getId());

        verify(eventDAO).insert(new Event(id, Data.Player1.PLAYER.getId(), Data.Player1.Planet1.PLANET.getId(), EventType.SHIP_COMPLETED, 1, Optional.empty()));
    }

    @Test
    public void testCreateFlightReturnedEvent() throws Exception {
        UUID id = UUID.randomUUID();
        when(uuidFactory.create()).thenReturn(id);

        sut.createFlightReturnedEvent(Data.Player1.PLAYER.getId(), Data.Player1.Planet1.PLANET.getId());

        verify(eventDAO).insert(new Event(id, Data.Player1.PLAYER.getId(), Data.Player1.Planet1.PLANET.getId(), EventType.FLIGHT_RETURNED, 1, Optional.empty()));
    }

    @Test
    public void testCreateTransferArrivedEvent() throws Exception {
        UUID id = UUID.randomUUID();
        when(uuidFactory.create()).thenReturn(id);

        sut.createTransferArrivedEvent(Data.Player1.PLAYER.getId(), Data.Player1.Planet1.PLANET.getId());

        verify(eventDAO).insert(new Event(id, Data.Player1.PLAYER.getId(), Data.Player1.Planet1.PLANET.getId(), EventType.TRANSFER_ARRIVED, 1, Optional.empty()));
    }

    @Test
    public void testCreateResearchCompletedEvent() throws Exception {
        UUID id = UUID.randomUUID();
        when(uuidFactory.create()).thenReturn(id);

        sut.createResearchCompletedEvent(Data.Player1.PLAYER.getId(), Data.Player1.Planet1.PLANET.getId());

        verify(eventDAO).insert(new Event(id, Data.Player1.PLAYER.getId(), Data.Player1.Planet1.PLANET.getId(), EventType.RESEARCH_COMPLETED, 1, Optional.empty()));
    }
}