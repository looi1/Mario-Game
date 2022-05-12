package game.ground;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.Status;
import game.items.Coin;

/**
 * A Wall class that represents Ground and implements Jumpable
 *
 * @see edu.monash.fit2099.engine.positions.Ground
 */
public class Wall extends Ground implements Jumpable{

	/**
	 * Constructor.
	 */
	public Wall() {
		super('#');
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
		return actor.hasCapability(Status.POWERSTAR) || actor.hasCapability(Status.FLY);
	}

	/**
	 * Override this to implement terrain that blocks thrown objects but not movement, or vice versa
	 *
	 * @return true
	 */
	@Override
	public boolean blocksThrownObjects() {
		return true;
	}

	/**
	 * Ground can also experience the joy of time.
	 * @param location The location of the Ground
	 */
	@Override
	public void tick(Location location) {
		super.tick(location);
		int x = location.x();
		int y = location.y();
		GameMap map = location.map();

		if (location.getActor() != null) {
			if (location.getActor().hasCapability(Status.POWERSTAR)) {
				location.setGround(new Dirt());
				location.addItem(new Coin(5));
			}
		}
	}

	/**
	 * Getter method for jump successRate
	 *
	 * @return Wall success rate when player want to jump
	 */
	public int getSuccessRate() {
		return 80;
	}

	/**
	 * Getter method for jump fallDamage
	 *
	 * @return Wall fall damage when player failed to jump
	 */
	public int getFallDamage() {
		return 20;
	}

	/**
	 * Getter method for the high ground name
	 *
	 * @return high ground name
	 */
	public String getName() {
		return "Wall";
	}
}
