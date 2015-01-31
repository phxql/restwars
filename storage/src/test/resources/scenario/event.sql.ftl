<#-- @ftlvariable name="events" type="restwars.model.event.Event[]" -->

<#list events as event>
INSERT INTO event VALUES (
'${event.id}', '${event.playerId}', '${event.planetId}', ${event.type.id}, ${event.round}, <#if event.fightId.isPresent()>'${event.getFightId().get()}'<#else>NULL</#if>
);
</#list>