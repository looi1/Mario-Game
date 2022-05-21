package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.Status;
import game.Wallet;
import game.items.Bottle;

import java.nio.file.StandardCopyOption;

/**
 * An ObtainItemAction Class that represents Action for obtaining items
 */
public class ObtainItemAction extends Action {

    /**
     * Item that can be obtained
     */
    private final Item item;

    /**
     * Constructor
     * @param item to be sold
     */
    public ObtainItemAction (Item item) {
        this.item = item;
    }

    /**
     * Perform the Action.
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return a description of what happened that can be displayed to the user.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        if (this.item instanceof Bottle) {
            if (!(actor.getInventory().contains(this.item))){
                actor.addItemToInventory(this.item);
            }
            return "Mario obtained Magical Bottle from Toad";
        } else {
            return "You already have a magical bottle in your inventory!";
        }
    }

    /**
     * Returns a descriptive string
     *
     * @param actor The actor performing the action.
     * @return the text we put on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return "Obtain " + this.item;
    }
}
