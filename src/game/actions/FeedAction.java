package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import game.Yoshi;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.items.SuperMushroom;
import game.Player;

public class FeedAction extends Action {
    
    private Yoshi yoshi;

    public FeedAction(Yoshi yoshi) {
        this.yoshi = yoshi;
    }
    
    @Override
    public String execute(Actor actor, GameMap map) {
        yoshi.eatSteak();
        return "Yoshi consumed Steak! Yoshi is now stronger!";
    }

    @Override
    public String menuDescription(Actor actor) {
        return "Feed Yoshi some steak";
    }

}
