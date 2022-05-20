package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.Player;
import game.Status;
import game.items.Bottle;
import game.items.Consumable;
import game.items.Fountains;

import java.util.ArrayList;

/**
 * A class that figures out an Action for consuming an item
 *
 * @see edu.monash.fit2099.engine.actions.Action
 */
public class ConsumeAction extends Action {
    /**
     * Item that the Player wants to consume
     */
    private final Item item;

    /**
     * Constructor
     *
     * @param item the item that wants to be consumed
     */
    public ConsumeAction(Item item) {
        this.item = item;
    }

    /**
     * Perform the Action.
     *
     * @param actor     The actor performing the action.
     * @param gameMap   The map the actor is on.
     * @return a description of what happened that can be displayed to the user.
     */
    @Override
    public String execute(Actor actor, GameMap gameMap) {
        Location location = gameMap.locationOf(actor);

        if (!(this.item instanceof Bottle)) {
            if (location.getItems().contains(this.item)) {
                location.removeItem(this.item);
            } else {
                actor.removeItemFromInventory(item);
            }
            Player player = (Player) actor;
            Consumable consumable = (Consumable) this.item;
            player.addEffect(consumable.getConsumeStatus());
        } else if (Bottle.getFountain().size() > 0){
            Fountains fountain = Bottle.getFountain().get(Bottle.getFountain().size() - 1);
            Player player = (Player) actor;
            if (fountain.hasCapability(Status.HEAL)){
                player.heal(50);
            } else {
                player.setDamage(15);
            }
            Bottle.getFountain().remove(Bottle.getFountain().size() - 1);
            return "Mario consumes " + fountain.getName();
        }else {
            return "The Bottle is empty";
        }
        return "Mario consumes " + this.item;
    }

    /**
     * Returns a descriptive string
     *
     * @param actor The actor performing the action.
     * @return the text we put on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        ArrayList<String> water = new ArrayList<>();

        if (actor.hasCapability(Status.HAS_BOTTLE) && this.item instanceof Bottle){
            for (int i = 0; i < Bottle.getFountain().size(); i++){
                water.add(Bottle.getFountain().get(i).getName());
            }
            return "Mario consumes " + this.item + water;
        }
        return "Consume " + item;
    }
}
