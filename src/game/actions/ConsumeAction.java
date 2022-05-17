package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.Player;
import game.items.Consumable;

public class ConsumeAction extends Action {
    private final Item item;

    
    public ConsumeAction(Item item) {
        this.item = item;
    };

    @Override
    public String execute(Actor actor, GameMap gameMap) {
        Location location = gameMap.locationOf(actor);
        if (location.getItems().contains(item)) {
            location.removeItem(item);
        } else {
            actor.removeItemFromInventory(item);
        }
        Player player = (Player) actor;
        Consumable consumable = (Consumable) item;
        player.addEffect(consumable.getConsumeStatus());
        return "Mario consumes " + item;
    }

    @Override
    public String menuDescription(Actor actor) {
        return "Consume " + item;
    }

}
