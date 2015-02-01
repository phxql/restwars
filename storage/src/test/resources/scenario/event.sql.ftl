<#-- @ftlvariable name="" type="restwars.storage.scenario.impl.EventScenario.Model" -->

<#list events as event>
INSERT INTO event VALUES (
'${event.id}', '${event.playerId}', '${event.planetId}', ${event.type.id}, ${event.round}, <#if event.fightId.isPresent()>'${event.getFightId().get()}'<#else>NULL</#if>
);
</#list>