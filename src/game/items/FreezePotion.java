package game.items;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;
import game.Status;
import game.actions.ConsumeAction;
import game.items.Consumable;

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

    public Enum<?> getConsumeStatus() {
        return Status.FREEZE;
    }
    
}
