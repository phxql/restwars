package restwars.service.player.impl;

import org.mockito.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import restwars.service.infrastructure.UUIDFactory;
import restwars.service.planet.PlanetService;
import restwars.service.player.Player;
import restwars.service.player.PlayerDAO;

import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

public class PlayerServiceImplTest {
    private UUIDFactory uuidFactory;
    private PlayerDAO playerDAO;
    private PlanetService planetService;
    private PlayerServiceImpl sut;

    @BeforeMethod
    public void setUp() throws Exception {
        uuidFactory = mock(UUIDFactory.class);
        playerDAO = mock(PlayerDAO.class);
        planetService = mock(PlanetService.class);

        sut = new PlayerServiceImpl(uuidFactory, playerDAO, planetService);

        when(playerDAO.findWithUsername(Matchers.anyString())).thenReturn(Optional.<Player>empty());
    }

    @Test
    public void testFindWithUsername() throws Exception {
        Player player = mock(Player.class);
        when(playerDAO.findWithUsername("Sheng Long")).thenReturn(Optional.of(player));

        Optional<Player> actual = sut.findWithUsername("Sheng Long");

        assertThat(actual, is(Optional.of(player)));
    }

    @Test
    public void testCreatePlayer() throws Exception {
        UUID id = UUID.randomUUID();
        when(uuidFactory.create()).thenReturn(id);

        Player actual = sut.createPlayer("username", "password");

        assertThat(actual.getId(), is(id));
        assertThat(actual.getUsername(), is("username"));
        assertThat(actual.getPassword(), is("password"));

        verify(playerDAO).insert(new Player(id, "username", "password"));
        verify(planetService).createStartPlanet(new Player(id, "username", "password"));
    }
}