package game.items;

import edu.monash.fit2099.engine.items.Item;
import game.actions.ConsumeSuperMushroomAction;

/**
 * class representing SuperMushroom
 */
public class SuperMushroom extends Item implements Sellable {

    /**
     * Constructor
     */
    public SuperMushroom() {
        super("Super Mushroom", '^', true);
        //super.addCapability(Status.SUPERMUSHROOM);
        super.addAction(new ConsumeSuperMushroomAction(this));
    }

    /**
     * get price of supermushroom
     * @return
     */
    public int getPrice() {
        return 400;
    }
}
    

