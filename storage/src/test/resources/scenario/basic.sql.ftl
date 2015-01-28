<#-- @ftlvariable name="players" type="restwars.model.player.Player[]" -->
<#-- @ftlvariable name="planets" type="restwars.model.planet.Planet[]" -->
<#-- @ftlvariable name="buildings" type="restwars.model.building.Building[]" -->

<#list players as player>
INSERT INTO player VALUES (
'${player.id}', '${player.username}', '${player.password}'
);
</#list>

<#list planets as planet>
INSERT INTO planet VALUES(
'${planet.id}', ${planet.location.galaxy}, ${planet.location.solarSystem}, ${planet.location.planet}, '${planet.ownerId}', ${planet.resources.crystals}, ${planet.resources.gas}, ${planet.resources.energy}
);
</#list>

<#list buildings as building>
INSERT INTO building VALUES(
'${building.id}', ${building.type.id}, ${building.level}, '${building.planetId}'
);
</#list>

