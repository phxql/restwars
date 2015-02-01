<#-- @ftlvariable name="" type="restwars.storage.scenario.impl.FightScenario.Model" -->

<#list fights as fight>
INSERT INTO fight VALUES (
'${fight.id}', '${fight.attackerId}', '${fight.defenderId}', '${fight.planetId}', ${fight.round}, ${fight.loot.crystals}, ${fight.loot.gas}
);

    <#list fight.attackingShips.asList() as ship>
    INSERT INTO fight_ships VALUES (
    '${fight.id}', 0, ${ship.type.id}, ${ship.amount}
    );
    </#list>

    <#list fight.defendingShips.asList() as ship>
    INSERT INTO fight_ships VALUES (
    '${fight.id}', 1, ${ship.type.id}, ${ship.amount}
    );
    </#list>

    <#list fight.remainingAttackerShips.asList() as ship>
    INSERT INTO fight_ships VALUES (
    '${fight.id}', 2, ${ship.type.id}, ${ship.amount}
    );
    </#list>

    <#list fight.remainingDefenderShips.asList() as ship>
    INSERT INTO fight_ships VALUES (
    '${fight.id}', 3, ${ship.type.id}, ${ship.amount}
    );
    </#list>
</#list>