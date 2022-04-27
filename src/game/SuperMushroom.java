package game;

import edu.monash.fit2099.engine.items.Item;

public class SuperMushroom extends Item implements SellableItem{

    public SuperMushroom() {
        super("Super Mushroom", '^', true);
        super.addCapability(Status.SUPERMUSHROOM);
        super.addAction(new ConsumeSuperMushroomAction(this));
    }

    public int getPrice() {
        return 400;
    }
}
    

