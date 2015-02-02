package restwars.service.fight.impl;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import restwars.model.fight.Fight;
import restwars.model.fight.FightWithPlanetAndPlayer;
import restwars.model.resource.Resources;
import restwars.model.ship.Ship;
import restwars.model.ship.ShipType;
import restwars.model.ship.Ships;
import restwars.service.Data;
import restwars.service.fight.FightDAO;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FightServiceImplTest {

    public static final FightWithPlanetAndPlayer FIGHT_WITH_PLANET_AND_PLAYER = new FightWithPlanetAndPlayer(
            new Fight(UUID.randomUUID(), Data.Player1.PLAYER.getId(), Data.Player2.PLAYER.getId(), Data.Player1.Planet1.PLANET.getId(),
                    new Ships(new Ship(ShipType.MOSQUITO, 1)), new Ships(new Ship(ShipType.COLONY, 1)),
                    new Ships(new Ship(ShipType.MOSQUITO, 1)), new Ships(new Ship(ShipType.COLONY, 1)), 1, new Resources(1, 2, 3)
            ), Data.Player1.Planet1.PLANET, Data.Player1.PLAYER, Data.Player2.PLAYER
    );
    private FightServiceImpl sut;
    private FightDAO fightDAO;

    @BeforeMethod
    public void setUp() throws Exception {
        fightDAO = mock(FightDAO.class);

        sut = new FightServiceImpl(fightDAO);
    }

    @Test
    public void testFindFight() throws Exception {
        UUID id = UUID.randomUUID();

        Optional<FightWithPlanetAndPlayer> expected = Optional.of(FIGHT_WITH_PLANET_AND_PLAYER);
        when(fightDAO.findWithId(id)).thenReturn(expected);

        Optional<FightWithPlanetAndPlayer> actual = sut.findFight(id);

        assertThat(actual, is(expected));
    }

    @Test
    public void testFindFightsWithPlayerSinceRound() throws Exception {
        List<FightWithPlanetAndPlayer> expected = Arrays.asList(FIGHT_WITH_PLANET_AND_PLAYER);
        when(fightDAO.findFightsWithPlayerSinceRound(Data.Player1.PLAYER.getId(), 1)).thenReturn(expected);

        List<FightWithPlanetAndPlayer> actual = sut.findFightsWithPlayerSinceRound(Data.Player1.PLAYER, 1);

        assertThat(actual, is(expected));
    }
}