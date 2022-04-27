package game;

import edu.monash.fit2099.engine.items.Item;

public class PowerStar extends Item implements SellableItem {

    public PowerStar() {
        super("Power Star", '*', true);
        super.addCapability(Status.POWERSTAR);
        super.addAction(new ConsumePowerStarAction(this));
    }

    public int getPrice() {
        return 600;
    }
}
