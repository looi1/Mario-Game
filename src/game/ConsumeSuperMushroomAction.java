package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

public class ConsumeSuperMushroomAction extends Action {

    private final SuperMushroom superMushroom;

    public ConsumeSuperMushroomAction(SuperMushroom superMushroom) {
        this.superMushroom = superMushroom;
    };

    @Override
    public String execute(Actor actor, GameMap gameMap) {
        actor.removeItemFromInventory(superMushroom);
        return "Player consumed SuperMushroom";
    }

    @Override
    public String menuDescription(Actor actor) {
        return "Consume SuperMushroom";
    }

    public String hotKey() {
        return "csm";
    }

}
