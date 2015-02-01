package restwars.storage.ship;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import restwars.model.flight.Flight;
import restwars.model.flight.FlightDirection;
import restwars.model.flight.FlightType;
import restwars.model.planet.Location;
import restwars.model.resource.Resources;
import restwars.model.ship.Ship;
import restwars.model.ship.ShipType;
import restwars.model.ship.Ships;
import restwars.storage.DatabaseTest;
import restwars.storage.scenario.MultipleScenarios;
import restwars.storage.scenario.Scenario;
import restwars.storage.scenario.impl.BasicScenario;
import restwars.storage.scenario.impl.FlightScenario;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class JooqFlightDAOTest extends DatabaseTest {
    private JooqFlightDAO sut;

    @Override
    @BeforeMethod
    public void setUp() throws Exception {
        super.setUp();

        sut = new JooqFlightDAO(getUnitOfWorkService());
    }

    @Test
    public void testInsert() throws Exception {
        Flight flight = new Flight(UUID.fromString("906e12d0-a99d-11e4-bcd8-0800200c9a66"), new Location(1, 2, 3),
                new Location(3, 2, 1), 10, 12, new Ships(new Ship(ShipType.DAEDALUS, 7), new Ship(ShipType.PROBE, 1)),
                100, FlightType.TRANSFER, BasicScenario.Player1.PLAYER.getId(), FlightDirection.RETURN,
                new Resources(100, 200, 0), 0.7, true);
        sut.insert(flight);

        List<Map<String, Object>> flightRows = select("SELECT * FROM flight WHERE id = ?", flight.getId());
        assertThat(flightRows, hasSize(1));
        verifyFlightRow(flightRows.get(0), flight);

        List<Map<String, Object>> flightShipsRows = select("SELECT * FROM flight_ships WHERE flight_id = ?", flight.getId());
        assertThat(flightShipsRows, hasSize(2));
        verifyFlightShipsRows(flightShipsRows, flight.getShips());
    }

    @Test
    public void testFindWithPlayerId() throws Exception {
        List<Flight> flights = sut.findWithPlayerId(BasicScenario.Player1.PLAYER.getId());

        assertThat(flights, hasSize(2));
        assertThat(flights, containsInAnyOrder(FlightScenario.Player1.FLIGHT_1, FlightScenario.Player1.FLIGHT_2));
    }

    @Test
    public void testFindWithArrival() throws Exception {
        Flight expected = FlightScenario.Player1.FLIGHT_1;
        List<Flight> flights = sut.findWithArrival(expected.getArrivalInRound());

        assertThat(flights, hasSize(1));
        assertThat(flights.get(0), is(expected));
    }

    @Test
    public void testFindWithArrival2() throws Exception {
        List<Flight> flights = sut.findWithArrival(100);

        assertThat(flights, hasSize(0));
    }

    @Test
    public void testUpdate() throws Exception {
        Flight flight = FlightScenario.Player1.FLIGHT_1;
        Flight updatedFlight = new Flight(flight.getId(), new Location(4, 4, 4), new Location(5, 5, 5), 12, 15,
                new Ships(new Ship(ShipType.DAEDALUS, 7), new Ship(ShipType.MULE, 2)), 12, FlightType.TRANSPORT,
                BasicScenario.Player2.PLAYER.getId(), FlightDirection.RETURN, new Resources(12, 24, 0), 3.5, true);

        sut.update(updatedFlight);

        List<Map<String, Object>> flightRows = select("SELECT * FROM flight WHERE id = ?", updatedFlight.getId());
        assertThat(flightRows, hasSize(1));
        verifyFlightRow(flightRows.get(0), updatedFlight);

        List<Map<String, Object>> flightShipsRows = select("SELECT * FROM flight_ships WHERE flight_id = ?", updatedFlight.getId());
        assertThat(flightShipsRows, hasSize(2));
        verifyFlightShipsRows(flightShipsRows, updatedFlight.getShips());
    }

    @Test
    public void testDelete() throws Exception {
        Flight flight = FlightScenario.Player1.FLIGHT_2;
        sut.delete(flight);

        List<Map<String, Object>> flightRows = select("SELECT * FROM flight WHERE id = ?", flight.getId());
        assertThat(flightRows, hasSize(0));

        List<Map<String, Object>> flightShipsRows = select("SELECT * FROM flight_ships WHERE flight_id = ?", flight.getId());
        assertThat(flightShipsRows, hasSize(0));
    }

    @Test
    public void testFindWithTypeAndDetected() throws Exception {
        Flight expected = FlightScenario.Player1.FLIGHT_1;
        List<Flight> flights = sut.findWithTypeAndDetected(expected.getType(), expected.isDetected());

        assertThat(flights, hasSize(1));
        assertThat(flights.get(0), is(expected));
    }

    @Test
    public void testFindWithTypeAndDetected2() throws Exception {
        List<Flight> flights = sut.findWithTypeAndDetected(FlightType.COLONIZE, false);

        assertThat(flights, hasSize(0));
    }

    @Test
    public void testFindWithStart() throws Exception {
        List<Flight> flights = sut.findWithStart(BasicScenario.Player1.Planet1.PLANET.getLocation());

        assertThat(flights, hasSize(2));
        assertThat(flights, containsInAnyOrder(FlightScenario.Player1.FLIGHT_1, FlightScenario.Player1.FLIGHT_2));
    }

    @Test
    public void testFindWithStart2() throws Exception {
        List<Flight> flights = sut.findWithStart(new Location(10, 10, 10));

        assertThat(flights, hasSize(0));
    }

    @Override
    protected Scenario getScenario() {
        return new MultipleScenarios(
                BasicScenario.create(),
                FlightScenario.create()
        );
    }

    private void verifyFlightShipsRows(List<Map<String, Object>> rows, Ships ships) {
        for (Map<String, Object> row : rows) {
            int type = (int) row.get("type");
            int amount = (int) row.get("amount");

            assertThat(ships.asList().stream().anyMatch(s -> s.getType().getId() == type && s.getAmount() == amount), is(true));
        }
    }

    private void verifyFlightRow(Map<String, Object> row, Flight flight) {
        assertThat(row.get("id"), is(flight.getId()));
        assertThat(row.get("player_id"), is(flight.getPlayerId()));
        assertThat(row.get("start_galaxy"), is(flight.getStart().getGalaxy()));
        assertThat(row.get("start_solar_system"), is(flight.getStart().getSolarSystem()));
        assertThat(row.get("start_planet"), is(flight.getStart().getPlanet()));
        assertThat(row.get("destination_galaxy"), is(flight.getDestination().getGalaxy()));
        assertThat(row.get("destination_solar_system"), is(flight.getDestination().getSolarSystem()));
        assertThat(row.get("destination_planet"), is(flight.getDestination().getPlanet()));
        assertThat(row.get("started_in_round"), is(flight.getStartedInRound()));
        assertThat(row.get("arrival_in_round"), is(flight.getArrivalInRound()));
        assertThat(row.get("energy_needed"), is(flight.getEnergyNeeded()));
        assertThat(row.get("type"), is(flight.getType().getId()));
        assertThat(row.get("direction"), is(flight.getDirection().getId()));
        assertThat(row.get("cargo_crystals"), is(flight.getCargo().getCrystals()));
        assertThat(row.get("cargo_gas"), is(flight.getCargo().getGas()));
        assertThat(row.get("speed"), is(flight.getSpeed()));
        assertThat(row.get("detected"), is(flight.isDetected()));
    }
}