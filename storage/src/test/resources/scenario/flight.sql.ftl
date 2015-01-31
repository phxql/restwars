<#-- @ftlvariable name="" type="restwars.storage.scenario.impl.FlightScenario.Model" -->

<#list flights as flight>
INSERT INTO flight VALUES (
'${flight.id}', '${flight.playerId}', ${flight.start.galaxy}, ${flight.start.solarSystem}, ${flight.start.planet}, ${flight.destination.galaxy},
${flight.destination.solarSystem}, ${flight.destination.planet}, ${flight.startedInRound}, ${flight.arrivalInRound}, ${flight.energyNeeded},
${flight.type.id}, ${flight.direction.id}, ${flight.cargo.crystals}, ${flight.cargo.gas}, ${flight.speed?c}, ${flight.detected?c}
);

    <#list flight.ships.asList() as ship>
    INSERT INTO flight_ships VALUES (
    '${flight.id}', ${ship.type.id}, ${ship.amount}
    );
    </#list>
</#list>

<#list detectedFlights as detectedFlight>
INSERT INTO detected_flight VALUES(
'${detectedFlight.flightId}', '${detectedFlight.playerId}', ${detectedFlight.approximatedFleetSize}
);
</#list>