<#-- @ftlvariable name="" type="restwars.storage.scenario.ConstructionSiteScenario.Model" -->

<#list constructionSites as constructionSite>
INSERT INTO construction_site VALUES (
'${constructionSite.id}', ${constructionSite.type.id}, ${constructionSite.level}, '${constructionSite.planetId}', '${constructionSite.playerId}', ${constructionSite.started}, ${constructionSite.done}
);
</#list>