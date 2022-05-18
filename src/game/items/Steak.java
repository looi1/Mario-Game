package game.items;

import edu.monash.fit2099.engine.items.Item;

/**
 * Class representing Steak
 * 
 */
public class Steak extends Item implements Sellable {

    /**
     * Constructor
     */
    public Steak() {
        super("Steak", 'S', false);
    }

    /**
     * Price
     */
    public int getPrice() {
        return 50;
    }


}
