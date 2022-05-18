package game.items;

import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * Class representing StinkBug, a sort of mine that hurts actors that step on it.
 */
public class StinkBug extends Item implements Sellable {

    /**
     * Whether the StinkBug is activated. It takes 1 turn for a new deployed StinkBug to activate.
     */
    private boolean armed = false;
    /**
     * GameMap StinkBug is on.
     */
    private GameMap map;
    

    /**
     * Constructor
     * @param map the GameMap the Stinkbug is on.
     */
    public StinkBug(GameMap map) {
        super("StinkBug", '~', true);
        this.map = map;
    }

    /**
     * Uses tick method to check if an actor is standing on its location and damages it.
     */
    @Override
    public void tick(Location location) {

        if (armed) {
            if (location.containsAnActor()) {
                Actor actor = location.getActor();
                actor.hurt(35);
                System.out.println(actor + " stepped on Stinkbug and suffered 35 damage!");
                location.removeItem(this);
                if (!actor.isConscious()) {
                    map.removeActor(actor);
                    System.out.println(actor + " was killed by Stinkbug");
                }
            }
        } else {
            armed = true;
            this.togglePortability();
        }
    }

    /**
     * get price of StinkBug
     */
    public int getPrice() {
        return 30;
    }
}
