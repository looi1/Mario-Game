package game.items;

import edu.monash.fit2099.engine.items.Item;

public class Steak extends Item implements Sellable {

    public Steak() {
        super("Steak", 'S', false);
    }

    public int getPrice() {
        return 50;
    }


}
