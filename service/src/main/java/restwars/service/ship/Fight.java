package restwars.service.ship;

public class Fight {
    private final Ships attackingShips;

    private final Ships defendingShips;

    private final Ships remainingAttackerShips;

    private final Ships remainingDefenderShips;

    public Fight(Ships attackingShips, Ships defendingShips, Ships remainingAttackerShips, Ships remainingDefenderShips) {
        this.attackingShips = attackingShips;
        this.defendingShips = defendingShips;
        this.remainingAttackerShips = remainingAttackerShips;
        this.remainingDefenderShips = remainingDefenderShips;
    }

    public Ships getAttackingShips() {
        return attackingShips;
    }

    public Ships getDefendingShips() {
        return defendingShips;
    }

    public Ships getRemainingAttackerShips() {
        return remainingAttackerShips;
    }

    public Ships getRemainingDefenderShips() {
        return remainingDefenderShips;
    }
}
