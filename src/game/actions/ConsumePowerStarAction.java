package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.items.PowerStar;
import game.Player;

/**
 * class to consume power star
 */
public class ConsumePowerStarAction extends Action {

    /**
     * a power star
     */
    private final PowerStar powerStar;

    /**
     * constructor
     * @param powerStar
     */
    public ConsumePowerStarAction(PowerStar powerStar) {
        this.powerStar = powerStar;
    };

    @Override
    public String execute(Actor actor, GameMap gameMap) {
        Location location = gameMap.locationOf(actor);
        if (location.getItems().contains(powerStar)) {
            location.removeItem(powerStar);
        } else {
            actor.removeItemFromInventory(powerStar);
        }
        Player player = (Player) actor;
        player.addPowerStarEffect();
        return "Mario consumes Power Star - 10 turns remaining";
    }

    @Override
    public String menuDescription(Actor actor) {
        return "Consume PowerStar";
    }

}