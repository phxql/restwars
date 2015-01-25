package restwars.storage.mapper;

import org.jooq.Record;
import restwars.model.player.Player;

import static restwars.storage.jooq.Tables.PLAYER;

public final class PlayerMapper {
    private PlayerMapper() {
    }

    public static Player fromRecord(Record record) {
        return fromRecord(record, PLAYER);
    }

    public static Player fromRecord(Record record, restwars.storage.jooq.tables.Player alias) {
        return new Player(
                record.getValue(alias.ID), record.getValue(alias.USERNAME), record.getValue(alias.PASSWORD)
        );
    }
}
