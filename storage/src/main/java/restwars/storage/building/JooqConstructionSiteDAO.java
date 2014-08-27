package restwars.storage.building;

import com.google.common.base.Preconditions;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.service.building.BuildingType;
import restwars.service.building.ConstructionSite;
import restwars.service.building.ConstructionSiteDAO;
import restwars.service.unitofwork.UnitOfWorkService;
import restwars.storage.jooq.AbstractJooqDAO;
import restwars.storage.jooq.tables.records.ConstructionSiteRecord;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static restwars.storage.jooq.Tables.CONSTRUCTION_SITE;

public class JooqConstructionSiteDAO extends AbstractJooqDAO implements ConstructionSiteDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(JooqConstructionSiteDAO.class);

    @Inject
    public JooqConstructionSiteDAO(UnitOfWorkService unitOfWorkService) {
        super(unitOfWorkService);
    }

    @Override
    public void insert(ConstructionSite constructionSite) {
        Preconditions.checkNotNull(constructionSite, "constructionSite");

        LOGGER.debug("Inserting construction site {}", constructionSite);

        context()
                .insertInto(
                        CONSTRUCTION_SITE, CONSTRUCTION_SITE.ID, CONSTRUCTION_SITE.TYPE, CONSTRUCTION_SITE.LEVEL,
                        CONSTRUCTION_SITE.PLANET_ID, CONSTRUCTION_SITE.STARTED, CONSTRUCTION_SITE.DONE)
                .values(
                        constructionSite.getId(), constructionSite.getType().getId(), constructionSite.getLevel(),
                        constructionSite.getPlanetId(), constructionSite.getStarted(), constructionSite.getDone()
                ).execute();
    }

    @Override
    public List<ConstructionSite> findWithPlanetId(UUID planetId) {
        Preconditions.checkNotNull(planetId, "planetId");

        LOGGER.debug("Finding construction sites with planet id {}", planetId);

        Result<ConstructionSiteRecord> result = context()
                .selectFrom(CONSTRUCTION_SITE).where(CONSTRUCTION_SITE.PLANET_ID.eq(planetId)).fetch();

        return result.stream().map(this::fromRecord).collect(Collectors.toList());
    }

    @Override
    public List<ConstructionSite> findWithDone(long round) {
        LOGGER.debug("Finding construction sites with done {}", round);

        Result<ConstructionSiteRecord> result = context()
                .selectFrom(CONSTRUCTION_SITE).where(CONSTRUCTION_SITE.DONE.eq(round)).fetch();

        return result.stream().map(this::fromRecord).collect(Collectors.toList());
    }

    @Override
    public void delete(ConstructionSite constructionSite) {
        Preconditions.checkNotNull(constructionSite, "constructionSite");

        LOGGER.debug("Deleting construction site {}", constructionSite);

        context().delete(CONSTRUCTION_SITE).where(CONSTRUCTION_SITE.ID.eq(constructionSite.getId())).execute();
    }

    private ConstructionSite fromRecord(ConstructionSiteRecord record) {
        assert record != null;

        return new ConstructionSite(
                record.getId(), BuildingType.fromId(record.getType()), record.getLevel(), record.getPlanetId(),
                record.getStarted(), record.getDone()
        );
    }
}
