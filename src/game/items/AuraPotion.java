package game.items;
import edu.monash.fit2099.engine.items.Item;
import game.Status;
import game.actions.ConsumeAction;


/**
 * Class representing AuraPotion, a Consumable object that grants a passive ability to damage all surrounding enemies and grant armor
 */
public class AuraPotion extends Item implements Sellable,Consumable {
    /**
     * constructor
     */
    public AuraPotion() {
        super("Aura Potion", '`', false);
        super.addAction(new ConsumeAction(this));
    }

    /**
     * Price
     * 
     */
    public int getPrice() {
        return 300;
    }

    public Enum<?> getConsumeStatus() {
        return Status.AURA;
    }


}
