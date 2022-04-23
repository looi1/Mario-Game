package game;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;

public class Coin extends Item implements Resettable{

    private final int value;
    
    

    // TODO: checks for appropriate coinValue
    /**
     * Constructor.
     */
    public Coin(int value) {
        super("coin(" + Integer.toString(value) + ")", '$', false);
        this.value = value;
        super.addAction(new CollectCoinAction(this));
        this.registerInstance();
    }

    /**
     * @return value of coin.
     */
    public int getValue() {
        return value;
    }

    @Override
    public void resetInstance(Actor actor, GameMap map) {





    }
}
