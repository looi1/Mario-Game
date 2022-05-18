package game.items;

import edu.monash.fit2099.engine.items.Item;
import game.Status;
import game.actions.ConsumeAction;

/**
 * FreezePotion, a consumable item that grants Player's attacks a chance to Freeze the enemy, making them unable to move or attack.
 */
public class FreezePotion extends Item implements Sellable,Consumable{

    /**
     * constructor
     */
    public FreezePotion() {
        super("Freeze Potion", '@', false);
        super.addAction(new ConsumeAction(this));
    }

    /**
     * method to get price of power star
     * @return
     */
    public int getPrice() {
        return 300;
    }

    /**
     * Get Status that FreezePotion will grant
     */
    public Enum<?> getConsumeStatus() {
        return Status.FREEZE;
    }
    
}
