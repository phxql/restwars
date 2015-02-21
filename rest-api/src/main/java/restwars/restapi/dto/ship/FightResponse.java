package restwars.restapi.dto.ship;

import com.google.common.base.Objects;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import restwars.restapi.dto.ResourcesResponse;

import java.util.List;

@ApiModel(description = "A fight")
public class FightResponse {
    @ApiModelProperty(value = "Id", required = true)
    private String id;

    @ApiModelProperty(value = "Planet", required = true)
    private String location;

    @ApiModelProperty(value = "Attacker", required = true)
    private String attacker;

    @ApiModelProperty(value = "Defender", required = true)
    private String defender;

    @ApiModelProperty(value = "Attacking ships", required = true)
    private List<ShipResponse> attackerShips;

    @ApiModelProperty(value = "Defending ships", required = true)
    private List<ShipResponse> defenderShips;

    @ApiModelProperty(value = "Remaining attacking ships after the fight", required = true)
    private List<ShipResponse> remainingAttackerShips;

    @ApiModelProperty(value = "Remaining defending ships after the fight", required = true)
    private List<ShipResponse> remainingDefenderShips;

    @ApiModelProperty(value = "Round in which the fight happened", required = true)
    private long round;

    @ApiModelProperty(value = "Loot", required = true)
    private ResourcesResponse loot;

    public FightResponse() {
    }

    public FightResponse(String id, String location, String attacker, String defender, List<ShipResponse> attackerShips, List<ShipResponse> defenderShips, List<ShipResponse> remainingAttackerShips, List<ShipResponse> remainingDefenderShips, long round, ResourcesResponse loot) {
        this.id = id;
        this.location = location;
        this.attacker = attacker;
        this.defender = defender;
        this.attackerShips = attackerShips;
        this.defenderShips = defenderShips;
        this.remainingAttackerShips = remainingAttackerShips;
        this.remainingDefenderShips = remainingDefenderShips;
        this.round = round;
        this.loot = loot;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAttacker() {
        return attacker;
    }

    public void setAttacker(String attacker) {
        this.attacker = attacker;
    }

    public String getDefender() {
        return defender;
    }

    public void setDefender(String defender) {
        this.defender = defender;
    }

    public List<ShipResponse> getAttackerShips() {
        return attackerShips;
    }

    public void setAttackerShips(List<ShipResponse> attackerShips) {
        this.attackerShips = attackerShips;
    }

    public List<ShipResponse> getDefenderShips() {
        return defenderShips;
    }

    public void setDefenderShips(List<ShipResponse> defenderShips) {
        this.defenderShips = defenderShips;
    }

    public List<ShipResponse> getRemainingAttackerShips() {
        return remainingAttackerShips;
    }

    public void setRemainingAttackerShips(List<ShipResponse> remainingAttackerShips) {
        this.remainingAttackerShips = remainingAttackerShips;
    }

    public List<ShipResponse> getRemainingDefenderShips() {
        return remainingDefenderShips;
    }

    public void setRemainingDefenderShips(List<ShipResponse> remainingDefenderShips) {
        this.remainingDefenderShips = remainingDefenderShips;
    }

    public long getRound() {
        return round;
    }

    public void setRound(long round) {
        this.round = round;
    }

    public ResourcesResponse getLoot() {
        return loot;
    }

    public void setLoot(ResourcesResponse loot) {
        this.loot = loot;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("location", location)
                .add("attacker", attacker)
                .add("defender", defender)
                .add("attackerShips", attackerShips)
                .add("defenderShips", defenderShips)
                .add("remainingAttackerShips", remainingAttackerShips)
                .add("remainingDefenderShips", remainingDefenderShips)
                .add("round", round)
                .add("loot", loot)
                .toString();
    }
}
