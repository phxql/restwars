package restwars.storage.player;

import com.google.common.base.Preconditions;
import org.jooq.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.model.player.Player;
import restwars.service.player.PlayerDAO;
import restwars.service.unitofwork.UnitOfWorkService;
import restwars.storage.jooq.AbstractJooqDAO;
import restwars.storage.mapper.PlayerMapper;

import javax.inject.Inject;
import java.util.Optional;

import static restwars.storage.jooq.Tables.PLAYER;

public class JooqPlayerDAO extends AbstractJooqDAO implements PlayerDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(JooqPlayerDAO.class);

    @Inject
    public JooqPlayerDAO(UnitOfWorkService unitOfWorkService) {
        super(unitOfWorkService);
    }

    @Override
    public void insert(Player player) {
        Preconditions.checkNotNull(player, "player");

        LOGGER.debug("Inserting player {}", player);

        context().insertInto(PLAYER, PLAYER.ID, PLAYER.USERNAME, PLAYER.PASSWORD)
                .values(player.getId(), player.getUsername(), player.getPassword())
                .execute();

    }

    @Override
    public Optional<Player> findWithUsername(String username) {
        Preconditions.checkNotNull(username, "username");

        LOGGER.debug("Finding player with username {}", username);

        Record record = context().select().from(PLAYER).where(PLAYER.USERNAME.eq(username)).fetchOne();
        if (record == null) {
            return Optional.empty();
        }

        return Optional.of(PlayerMapper.fromRecord(record));
    }
}
