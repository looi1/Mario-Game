package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

public class ConsumeSuperMushroomAction extends Action {
    @Override
    public String execute(Actor actor, GameMap map) {
        // player.addSupermushroomeffect
        return "Player consumed SuperMushroom";
    }

    @Override
    public String menuDescription(Actor actor) {
        return "Consume SuperMushroom";
    }

    // if player.hp < initial
    // 
}
