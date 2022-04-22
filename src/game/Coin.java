package game;

import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;

public class Coin extends Item {

    private final int value;
    
    

    // TODO: checks for appropriate coinValue
    /**
     * Constructor.
     */
    public Coin(int value) {
        super("coin(" + Integer.toString(value) + ")", '$', false);
        this.value = value;
        super.addAction(new CollectCoinAction(this));
    }

    /**
     * @return value of coin.
     */
    public int getValue() {
        return value;
    }   
}
