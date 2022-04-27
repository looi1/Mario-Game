package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

public class ConsumePowerStarAction extends Action {

    private final PowerStar powerStar;

    public ConsumePowerStarAction(PowerStar powerStar) {
        this.powerStar = powerStar;
    };

    @Override
    public String execute(Actor actor, GameMap gameMap) {
        actor.removeItemFromInventory(powerStar);
        return "Player consumed PowerStar";
    }

    @Override
    public String menuDescription(Actor actor) {
        return "Consume PowerStar";
    }

    public String hotKey() {
        return "cps";
    }

}