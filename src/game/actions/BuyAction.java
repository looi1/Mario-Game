package game.actions;

import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.behaviours.Behaviour;
import game.items.Bottle;
import game.items.Sellable;
import game.Wallet;
/**
 * Action for buying items
 */
public class BuyAction extends Action {

    /**
     * Item that can be sold
     */
    private final Sellable item;
    
    /**
     * Constructor
     * @param item to be sold
     */
    public BuyAction(Sellable item) {
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
        if (item.getPrice() <= Wallet.totalBalance) {
            Wallet.totalBalance -= item.getPrice();
            actor.addItemToInventory((Item) item);
            return "Bought " + item;
        } else {
            return "You don't have enough coins!";
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
        return "Buy " + item + " ($" + item.getPrice() + ")" ;
    }

    
}
