package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import game.Yoshi;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.items.Steak;

/**
 * Action to feed Yoshi
 */
public class FeedAction extends Action {
    /**
     * Yoshi
     */
    private Yoshi yoshi;
    /**
     * the Steak object
     */
    private Steak steak;

    /**
     * Constructor
     * @param yoshi
     * @param steak
     */
    public FeedAction(Yoshi yoshi, Steak steak) {
        this.yoshi = yoshi;
        this.steak = steak;
    }
    
    /**
     * Feed Yoshi the steak
     * Remove from inventory the steak
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        yoshi.eatSteak();
        actor.removeItemFromInventory(steak);
        return "Yoshi consumed Steak! Yoshi is now stronger!";
    }

    /**
     * Description
     */
    @Override
    public String menuDescription(Actor actor) {
        return "Feed Yoshi some steak";
    }

}
