<#-- @ftlvariable name="" type="restwars.storage.scenario.impl.ResearchScenario.Model" -->

<#list researches as research>
INSERT INTO research VALUES (
'${research.id}', ${research.type.id}, ${research.level}, '${research.planetId}', '${research.playerId}', ${research.started}, ${research.done}, ${research.researchCost.crystals}, ${research.researchCost.gas}, ${research.researchCost.energy}
);
</#list>
