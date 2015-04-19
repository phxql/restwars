package restwars.model.points;

import restwars.model.player.Player;

/**
 * Player with points.
 */
public class PlayerWithPoints {
    private final Player player;

    private final long points;

    /**
     * Constructor.
     *
     * @param player Player.
     * @param points Points.
     */
    public PlayerWithPoints(Player player, long points) {
        this.player = player;
        this.points = points;
    }

    public Player getPlayer() {
        return player;
    }

    public long getPoints() {
        return points;
    }
}
