package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import game.Yoshi;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.items.SuperMushroom;
import game.Player;
import game.items.Steak;

public class FeedAction extends Action {
    
    private Yoshi yoshi;
    private Steak steak;

    public FeedAction(Yoshi yoshi, Steak steak) {
        this.yoshi = yoshi;
        this.steak = steak;
    }
    
    @Override
    public String execute(Actor actor, GameMap map) {
        yoshi.eatSteak();
        actor.removeItemFromInventory(steak);
        return "Yoshi consumed Steak! Yoshi is now stronger!";
    }

    @Override
    public String menuDescription(Actor actor) {
        return "Feed Yoshi some steak";
    }

}
