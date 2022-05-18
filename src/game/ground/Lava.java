package game.ground;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.Status;

import java.util.Random;

public class Lava extends Ground {

    /**
     * instantiate a new Random class r
     */
    private final Random r = new Random();

    /**
     * Constructor.
     */
    public Lava() {
        super('L');
    }

    /**
     * Lava can also experience the joy of time.
     * @param location The location of the Lava
     */
    @Override
    public void tick(Location location) {
        super.tick(location);

        if (location.getActor() != null) {
            if (location.getActor().getDisplayChar() == 'm' || location.getActor().getDisplayChar() == 'M') {
                location.getActor().hurt(15);
            }
        }
    }

    /**
     * Returns true if an Actor can enter this location.
     *
     * Actors can enter a location if the terrain is passable and there isn't an Actor there already.
     * Game rule. One actor per location.
     * @param actor the Actor who might be moving
     * @return true if the Actor can enter this location
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        if (actor.getDisplayChar() == 'm' || actor.getDisplayChar() == 'M' || actor.getDisplayChar() == 'F') {
            return actor.hasCapability(Status.POWERSTAR) || actor.hasCapability(Status.FLY);
        }
        return false;
    }
}
