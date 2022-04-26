package game;

import edu.monash.fit2099.engine.items.Item;

public class SuperMushroom extends Item implements SellableItem{

    private int price = 400;

    public SuperMushroom() {
        super("Super Mushroom", '^', true);
        // player.status has no supermushroom, 
        super.addAction(new ConsumeSuperMushroomAction());


    }


    public int getPrice() {
        return price;
    }
}
    

