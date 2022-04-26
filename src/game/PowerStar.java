package game;

import edu.monash.fit2099.engine.items.Item;

public class PowerStar extends Item implements SellableItem, Consumables{

    private int price = 600;

    public PowerStar() {
        super("Power Star", '*', true);
    }

    public int getPrice() {
        return price;
    }

    public void consume() {
        
    }
}
