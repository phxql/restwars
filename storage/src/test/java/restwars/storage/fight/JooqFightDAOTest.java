package restwars.storage.fight;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import restwars.model.fight.Fight;
import restwars.model.fight.FightWithPlanetAndPlayer;
import restwars.model.resource.Resources;
import restwars.model.ship.Ship;
import restwars.model.ship.ShipType;
import restwars.model.ship.Ships;
import restwars.storage.DatabaseTest;
import restwars.storage.scenario.MultipleScenarios;
import restwars.storage.scenario.Scenario;
import restwars.storage.scenario.impl.BasicScenario;
import restwars.storage.scenario.impl.FightScenario;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class JooqFightDAOTest extends DatabaseTest {
    private JooqFightDAO sut;

    @Override
    @BeforeMethod
    public void setUp() throws Exception {
        super.setUp();

        sut = new JooqFightDAO(getUnitOfWorkService());
    }

    @Test
    public void testFindWithId() throws Exception {
        Fight expected = FightScenario.Player1.FIGHT;
        Optional<FightWithPlanetAndPlayer> fight = sut.findWithId(expected.getId());

        assertThat(fight.isPresent(), is(true));
        assertThat(fight.get(), is(new FightWithPlanetAndPlayer(expected, BasicScenario.Player2.Planet1.PLANET, BasicScenario.Player1.PLAYER, BasicScenario.Player2.PLAYER)));
    }

    @Test
    public void testFindWithId2() throws Exception {
        Optional<FightWithPlanetAndPlayer> fight = sut.findWithId(UUID.randomUUID());

        assertThat(fight.isPresent(), is(false));
    }

    @Test
    public void testInsert() throws Exception {
        Fight fight = new Fight(
                UUID.fromString("df40ccf7-6fd8-4c13-8eef-7742a87a55b0"), BasicScenario.Player1.PLAYER.getId(), BasicScenario.Player2.PLAYER.getId(),
                BasicScenario.Player2.Planet1.PLANET.getId(), new Ships(new Ship(ShipType.MOSQUITO, 5), new Ship(ShipType.MULE, 1)),
                new Ships(new Ship(ShipType.DAEDALUS, 1)), new Ships(new Ship(ShipType.MOSQUITO, 1)), new Ships(new Ship(ShipType.DAEDALUS, 1)),
                10, new Resources(10, 20, 0)
        );
        sut.insert(fight);

        List<Map<String, Object>> fightRows = select("SELECT * FROM fight WHERE id = ?", fight.getId());
        List<Map<String, Object>> fightShipsRows = select("SELECT * FROM fight_ships WHERE fight_id = ?", fight.getId());

        assertThat(fightRows, hasSize(1));
        verifyFightRow(fightRows.get(0), fight);

        assertThat(fightShipsRows, hasSize(5));
        verifyFightShipsRows(fightShipsRows, fight);
    }

    @Test
    public void testFindFightsWithPlayerSinceRound() throws Exception {
        Fight expected = FightScenario.Player1.FIGHT;
        // Find for attacker
        List<FightWithPlanetAndPlayer> fight = sut.findFightsWithPlayerSinceRound(expected.getAttackerId(), expected.getRound());

        assertThat(fight, hasSize(1));
        assertThat(fight.get(0), is(new FightWithPlanetAndPlayer(expected, BasicScenario.Player2.Planet1.PLANET, BasicScenario.Player1.PLAYER, BasicScenario.Player2.PLAYER)));
    }

    @Test
    public void testFindFightsWithPlayerSinceRound2() throws Exception {
        Fight expected = FightScenario.Player1.FIGHT;
        // Find for defender
        List<FightWithPlanetAndPlayer> fight = sut.findFightsWithPlayerSinceRound(expected.getDefenderId(), expected.getRound());

        assertThat(fight, hasSize(1));
        assertThat(fight.get(0), is(new FightWithPlanetAndPlayer(expected, BasicScenario.Player2.Planet1.PLANET, BasicScenario.Player1.PLAYER, BasicScenario.Player2.PLAYER)));
    }

    @Test
    public void testFindFightsWithPlayerSinceRound3() throws Exception {
        // Round doesn't exist
        List<FightWithPlanetAndPlayer> fight = sut.findFightsWithPlayerSinceRound(FightScenario.Player1.FIGHT.getAttackerId(), 100);

        assertThat(fight, hasSize(0));
    }


    @Test
    public void testFindFightsWithPlayerSinceRound4() throws Exception {
        // Player id doesn't exist
        List<FightWithPlanetAndPlayer> fight = sut.findFightsWithPlayerSinceRound(UUID.randomUUID(), 100);

        assertThat(fight, hasSize(0));
    }

    @Override
    protected Scenario getScenario() {
        return new MultipleScenarios(
                BasicScenario.create(),
                FightScenario.create()
        );
    }

    private void verifyFightShipsRows(List<Map<String, Object>> rows, Fight fight) {
        for (Ship ship : fight.getAttackingShips()) {
            assertThat(rows.stream().anyMatch(r -> matchFightShipsRow(fight, ship, r, 0)), is(true));
        }

        for (Ship ship : fight.getDefendingShips()) {
            assertThat(rows.stream().anyMatch(r -> matchFightShipsRow(fight, ship, r, 1)), is(true));
        }

        for (Ship ship : fight.getRemainingAttackerShips()) {
            assertThat(rows.stream().anyMatch(r -> matchFightShipsRow(fight, ship, r, 2)), is(true));
        }

        for (Ship ship : fight.getRemainingDefenderShips()) {
            assertThat(rows.stream().anyMatch(r -> matchFightShipsRow(fight, ship, r, 3)), is(true));
        }
    }

    private boolean matchFightShipsRow(Fight fight, Ship ship, Map<String, Object> row, int category) {
        return row.get("fight_id").equals(fight.getId()) &&
                row.get("category").equals(category) &&
                row.get("type").equals(ship.getType().getId()) &&
                row.get("amount").equals(ship.getAmount());
    }

    private void verifyFightRow(Map<String, Object> row, Fight fight) {
        assertThat(row.get("id"), is(fight.getId()));
        assertThat(row.get("attacker_id"), is(fight.getAttackerId()));
        assertThat(row.get("defender_id"), is(fight.getDefenderId()));
        assertThat(row.get("planet_id"), is(fight.getPlanetId()));
        assertThat(row.get("round"), is(fight.getRound()));
        assertThat(row.get("crystals_looted"), is(fight.getLoot().getCrystals()));
        assertThat(row.get("gas_looted"), is(fight.getLoot().getGas()));
    }
}