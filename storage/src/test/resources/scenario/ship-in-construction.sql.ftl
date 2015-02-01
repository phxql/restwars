<#-- @ftlvariable name="" type="restwars.storage.scenario.impl.ShipInConstructionScenario.Model" -->

<#list shipsInConstruction as sic>
INSERT INTO ship_in_construction VALUES (
'${sic.id}', ${sic.type.id}, '${sic.planetId}', '${sic.playerId}', ${sic.started}, ${sic.done}
);
</#list>