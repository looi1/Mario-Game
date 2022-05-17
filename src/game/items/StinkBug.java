package game.items;

import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.actors.Actor;;

public class StinkBug extends Item implements Sellable {

    private boolean armed;

    public StinkBug() {
        super("StinkBug", '~', true);
    }

    @Override
    public void tick(Location location) {

        if (armed) {
            if (location.containsAnActor()) {
                Actor actor = location.getActor();
                actor.hurt(35);
                System.out.println(actor + " stepped on Stinkbug and suffered 35 damage!");
                location.removeItem(this);
            }
        } else {
            armed = true;
            this.togglePortability();
        }
    }

    public int getPrice() {
        return 30;
    }
}
