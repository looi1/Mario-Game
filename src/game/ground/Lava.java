package game.ground;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;

/**
 * A Mature class that represents Ground
 *
 * @see edu.monash.fit2099.engine.positions.Ground
 */
public class Lava extends Ground {

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

        // Check if there is a Player on top of the Lava.
        if (location.getActor() != null) {

            // If yes, hurt the Player by 15 damage each turn.
            if (location.getActor().getDisplayChar() == 'm' || location.getActor().getDisplayChar() == 'M') {
                location.getActor().hurt(15);
                System.out.println(location.getActor() + " stepped on Lava and suffered 15 damage!");

                // If the player died by the Lava, the game is over.
                if (!location.getActor().isConscious()) {
                    location.map().removeActor(location.getActor());
                    System.out.println(location.getActor() + " was killed by Lava");
                }
            }
        }
    }

    /**
     * Returns true if an Actor can enter this location.
     * Only Player can step on Lava.
     *
     * Actors can enter a location if the terrain is passable and there isn't an Actor there already.
     * Game rule. One actor per location.
     * @param actor the Actor who might be moving
     * @return true if the Actor can enter this location
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        return actor.getDisplayChar() == 'm' || actor.getDisplayChar() == 'M';
    }
}
