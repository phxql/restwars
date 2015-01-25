package restwars.service.flight.impl.flighthandler;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.mechanics.ShipMechanics;
import restwars.service.event.EventService;
import restwars.service.fight.Fight;
import restwars.service.fight.FightDAO;
import restwars.service.flight.DetectedFlightDAO;
import restwars.service.flight.Flight;
import restwars.service.flight.FlightDAO;
import restwars.service.infrastructure.RandomNumberGenerator;
import restwars.service.infrastructure.RoundService;
import restwars.service.infrastructure.UUIDFactory;
import restwars.service.planet.Planet;
import restwars.service.planet.PlanetDAO;
import restwars.service.resource.Resources;
import restwars.service.ship.Hangar;
import restwars.service.ship.HangarDAO;
import restwars.service.ship.Ships;

import java.util.Optional;
import java.util.UUID;

public class AttackFlightHandler extends AbstractFlightHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(AttackFlightHandler.class);

    private final FightCalculator fightCalculator;
    private final FightDAO fightDAO;

    public AttackFlightHandler(RoundService roundService, FlightDAO flightDAO, PlanetDAO planetDAO, HangarDAO hangarDAO, UUIDFactory uuidFactory, FightDAO fightDAO, EventService eventService, RandomNumberGenerator randomNumberGenerator, DetectedFlightDAO detectedFlightDAO, ShipMechanics shipMechanics) {
        super(roundService, flightDAO, planetDAO, hangarDAO, uuidFactory, eventService, detectedFlightDAO, shipMechanics);

        this.fightDAO = Preconditions.checkNotNull(fightDAO, "fightDAO");
        this.fightCalculator = new FightCalculator(uuidFactory, randomNumberGenerator, shipMechanics);
    }

    @Override
    public void handle(Flight flight, long round) {
        assert flight != null;
        LOGGER.debug("Handling attack of flight {}", flight);

        Optional<Planet> planet = getPlanetDAO().findWithLocation(flight.getDestination());
        if (planet.isPresent()) {
            Planet defenderPlanet = planet.get();

            UUID defenderId = defenderPlanet.getOwnerId();
            if (defenderId.equals(flight.getPlayerId())) {
                LOGGER.debug("Planet {} is friendly, creating return flight", flight.getDestination());
                // Planet is friendly, create return flight
                createReturnFlight(flight, flight.getShips(), flight.getCargo());
            } else {
                Hangar hangar = getOrCreateHangar(defenderPlanet.getId(), defenderId);
                UUID attackerId = flight.getPlayerId();
                Fight fight = fightCalculator.attack(attackerId, defenderId, defenderPlanet.getId(), flight.getShips(), hangar.getShips(), round);

                // Update defenders hangar
                getHangarDAO().update(hangar.withShips(fight.getRemainingDefenderShips()));

                if (fight.getRemainingAttackerShips().isEmpty()) {
                    LOGGER.debug("Attacker lost all ships");

                    getDetectedFlightDAO().delete(flight.getId());
                    getFlightDAO().delete(flight);
                } else {
                    LOGGER.debug("Looting planet {}", defenderPlanet);
                    Resources loot = lootPlanet(defenderPlanet, fight.getRemainingAttackerShips());

                    fight = fight.withLoot(loot);

                    createReturnFlight(flight, fight.getRemainingAttackerShips(), loot);
                }

                // Store fight
                fightDAO.insert(fight);

                // Create event for attacker and defender
                getEventService().createFightHappenedEvent(attackerId, defenderId, defenderPlanet.getId(), fight.getId());
            }
        } else {
            LOGGER.debug("Planet {} is not colonized, creating return flight", flight.getDestination());
            // Planet is not colonized, create return flight
            createReturnFlight(flight, flight.getShips(), flight.getCargo());
        }
    }

    private Resources lootPlanet(Planet planet, Ships ships) {
        long storageCapacity = getShipUtils().calculateStorageCapacity(ships, getShipMechanics());

        long lootCrystals = storageCapacity / 2;
        long lootGas = storageCapacity - lootCrystals;

        // TODO - Gameplay: Implement a more greedy looting strategy
        lootCrystals = Math.min(planet.getResources().getCrystals(), lootCrystals);
        lootGas = Math.min(planet.getResources().getGas(), lootGas);

        // Decrease resources on planet
        planet = planet.withResources(planet.getResources().minus(new Resources(lootCrystals, lootGas, 0)));
        getPlanetDAO().update(planet);

        Resources resources = new Resources(lootCrystals, lootGas, 0);
        LOGGER.debug("Looted {} from planet {}", resources, planet.getLocation());
        return resources;
    }
}
