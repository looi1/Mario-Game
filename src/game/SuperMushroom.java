package game;

import edu.monash.fit2099.engine.items.Item;

public class SuperMushroom extends Item implements SellableItem, Consumables{

    private int price = 400;

    public SuperMushroom() {
        super("Super Mushroom", '^', true);
        // player.status has no supermushroom, 
        super.addAction(new ConsumeAction(this));


    }

    public void consume() {
        
    }
    


    public int getPrice() {
        return price;
    }
}
    

