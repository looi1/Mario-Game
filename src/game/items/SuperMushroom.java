package game.items;

import edu.monash.fit2099.engine.items.Item;
import game.Status;
import game.actions.ConsumeAction;
import game.items.Consumable;

/**
 * class representing SuperMushroom
 */
public class SuperMushroom extends Item implements Sellable, Consumable {

    /**
     * Constructor
     */
    public SuperMushroom() {
        super("Super Mushroom", '^', true);
        super.addAction(new ConsumeAction(this));
    }

    /**
     * get price of supermushroom
     * @return price
     */
    public int getPrice() {
        return 400;
    }

    public Enum<?> getConsumeStatus() {
        return Status.SUPERMUSHROOM;
    }
}
    

