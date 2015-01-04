package restwars.storage.mapper;

import org.jooq.Record;
import restwars.service.player.Player;

import static restwars.storage.jooq.Tables.PLAYER;

public final class PlayerMapper {
    private PlayerMapper() {
    }

    public static Player fromRecord(Record record) {
        return new Player(
                record.getValue(PLAYER.ID), record.getValue(PLAYER.USERNAME), record.getValue(PLAYER.PASSWORD)
        );
    }
}
