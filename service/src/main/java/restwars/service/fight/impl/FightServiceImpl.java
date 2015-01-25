package restwars.service.fight.impl;

import com.google.common.base.Preconditions;
import restwars.model.fight.FightWithPlanetAndPlayer;
import restwars.model.player.Player;
import restwars.service.fight.FightDAO;
import restwars.service.fight.FightService;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FightServiceImpl implements FightService {
    private final FightDAO fightDAO;

    @Inject
    public FightServiceImpl(FightDAO fightDAO) {
        this.fightDAO = Preconditions.checkNotNull(fightDAO, "fightDAO");
    }

    @Override
    public Optional<FightWithPlanetAndPlayer> findFight(UUID id) {
        return fightDAO.findWithId(id);
    }

    @Override
    public List<FightWithPlanetAndPlayer> findFightsWithPlayerSinceRound(Player player, long round) {
        Preconditions.checkNotNull(player, "player");

        return fightDAO.findFightsWithPlayerSinceRound(player.getId(), round);
    }
}
