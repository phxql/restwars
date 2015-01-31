package restwars.storage.scenario.impl;

import restwars.model.flight.DetectedFlight;
import restwars.model.flight.Flight;
import restwars.model.flight.FlightDirection;
import restwars.model.flight.FlightType;
import restwars.model.resource.Resources;
import restwars.model.ship.Ship;
import restwars.model.ship.ShipType;
import restwars.model.ship.Ships;
import restwars.storage.scenario.AbstractFreemarkerScenario;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class FlightScenario extends AbstractFreemarkerScenario<FlightScenario.Model> {
    public static class Player1 {
        public static final Flight FLIGHT_1 = new Flight(
                UUID.fromString("63728ee0-a999-11e4-bcd8-0800200c9a66"),
                BasicScenario.Player1.Planet1.PLANET.getLocation(), BasicScenario.Player2.Planet1.PLANET.getLocation(),
                1, 5, new Ships(new Ship(ShipType.MOSQUITO, 5), new Ship(ShipType.MULE, 1)), 100, FlightType.ATTACK,
                BasicScenario.Player1.PLAYER.getId(), FlightDirection.OUTWARD, Resources.NONE, 1.5, true
        );

        public static final Flight FLIGHT_2 = new Flight(
                UUID.fromString("eb90c640-a9a1-11e4-bcd8-0800200c9a66"), BasicScenario.Player1.Planet1.PLANET.getLocation(),
                BasicScenario.Player1.Planet2.PLANET.getLocation(), 10, 13, new Ships(new Ship(ShipType.MULE, 1)), 100,
                FlightType.TRANSPORT, BasicScenario.Player1.PLAYER.getId(), FlightDirection.OUTWARD,
                new Resources(100, 100, 0), 1.5, false
        );

        public static final DetectedFlight DETECTED_FLIGHT = new DetectedFlight(
                FLIGHT_1.getId(), FLIGHT_1.getPlayerId(), 100
        );
    }

    public static class Model {
        private final List<Flight> flights;
        private final List<DetectedFlight> detectedFlights;

        public Model(List<Flight> flights, List<DetectedFlight> detectedFlights) {
            this.flights = flights;
            this.detectedFlights = detectedFlights;
        }

        public List<Flight> getFlights() {
            return flights;
        }

        public List<DetectedFlight> getDetectedFlights() {
            return detectedFlights;
        }
    }

    private static final FlightScenario INSTANCE = new FlightScenario();

    public static FlightScenario create() {
        return INSTANCE;
    }

    @Override
    protected Model getModel() {
        List<Flight> flights = Arrays.asList(
                Player1.FLIGHT_1, Player1.FLIGHT_2
        );
        List<DetectedFlight> detectedFlights = Arrays.asList(
                Player1.DETECTED_FLIGHT
        );

        return new Model(flights, detectedFlights);
    }

    @Override
    protected String getTemplateName() {
        return "scenario/flight.sql.ftl";
    }
}
