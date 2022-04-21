package game;

import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;

public class Coin extends Item {

    private int value;
    
    

    // TODO: checks for appropriate coinValue
    public Coin(int value) {
        super("coin(" + Integer.toString(value) + ")", '$', false);
        this.value = value;
        super.addAction(new CollectCoinAction(this));
    }

    public int getValue() {
        return value;
    }

    public void collectByPlayer() {
        
    }

    
}
