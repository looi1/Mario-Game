package game.items;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.ConsumePowerStarAction;

/**
 * a class representing PowerStar
 */
public class PowerStar extends Item implements Sellable {

    /**
     * life span of powerstar
     */
    private int lifeSpan;

    /**
     * constructor
     */
    public PowerStar() {
        super("Power Star", '*', true);
        this.lifeSpan = 10;
        super.addAction(new ConsumePowerStarAction(this));
    }

    /**
     * method to remove powerstar from map
     * @param location
     */
    @Override
    public void tick(Location location) {
        if (lifeSpan <= 0) {
            location.removeItem(this);
        } else {
            lifeSpan -= 1;
        }
    }

    /**
     * method to remove power star from player inventory
     * @param currentLocation The location of the actor carrying this Item.
     * @param actor The actor carrying this Item.
     */
    @Override
    public void tick(Location currentLocation, Actor actor) {
        if (lifeSpan <= 0) {
            actor.removeItemFromInventory(this);
        } else {
            lifeSpan -= 1;
        }
	}

    /**
     * method to get price of power star
     * @return
     */
    public int getPrice() {
        return 600;
    }
    
    

    
}
