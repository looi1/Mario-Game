package game;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;


public class PowerStar extends Item implements Sellable {

    private int lifeSpan;

    public PowerStar() {
        super("Power Star", '*', true);
        this.lifeSpan = 10;
        //super.addCapability(Status.POWERSTAR);
        super.addAction(new ConsumePowerStarAction(this));
    }

    @Override
    public void tick(Location location) {
        if (lifeSpan <= 0) {
            location.removeItem(this);
        } else {
            lifeSpan -= 1;
        }
    }

    @Override
    public void tick(Location currentLocation, Actor actor) {
        if (lifeSpan <= 0) {
            actor.removeItemFromInventory(this);
        } else {
            lifeSpan -= 1;
        }
	}

    

    public int getPrice() {
        return 600;
    }
    
    

    
}
