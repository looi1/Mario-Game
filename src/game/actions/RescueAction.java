package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * class representing the rescue action
 */
public class RescueAction extends Action {

    /**
     * constructor
     */
    public RescueAction(){

    }

    @Override
    public String execute(Actor actor, GameMap map) {
        String result = "";

        map.removeActor(actor);
        result+="Congratulation! Princess Peach is rescued! You won the game!";

        return result;
    }

    @Override
    public String menuDescription(Actor actor) {
        return "Rescue Princess";
    }
}
