package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.items.Item;

public class ConsumeAction extends Action {

    private final Item item;
    public ConsumeAction(Item item) {
        this.item = item;
    }

    @Override
    public String execute(Actor actor, GameMap gameMap) {
        player.addCapability(item.capability);
        item.consume();
        
        return "Player consumed " + item;
    }

    @Override
    public String menuDescription(Actor actor) {
        return "Consume " + item;
    }
    
}
