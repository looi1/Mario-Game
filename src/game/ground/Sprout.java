package game.ground;

import edu.monash.fit2099.engine.positions.Location;
import game.Status;
import game.enemies.Goomba;
import game.items.Coin;

import java.util.Random;

/**
 * A Sprout class that represents a Tree
 *
 * @see edu.monash.fit2099.engine.positions.Ground
 */
public class Sprout extends Tree implements Jumpable{
    /**
     * sprout age that adds each turn
     */
    private int sproutAge = 0;

    /**
     * instantiate a new Random class r
     */
    private final Random r = new Random();

    /**
     * Constructor
     */
    public Sprout(){
        super('+');
    }

    /**
     * Sapling can also experience the joy of time.
     * @param location The location of the Sapling
     */
    @Override
    public void tick(Location location) {
        super.tick(location);
        sproutAge += 1;
        int spawnRateGoomba = 10;

        int high = 101;
        int low = 0;
        int random = r.nextInt((high - low) + low);

        if (location.getActor() != null) {
            if (location.getActor().hasCapability(Status.POWERSTAR)) {
                location.setGround(new Dirt());
                location.addItem(new Coin(5));
            }
        }

        if (random <= spawnRateGoomba && location.getGround().getDisplayChar() == '+'){
            if (location.getActor() == null){
                location.addActor(new Goomba());
            }
        }

        if (sproutAge == 10 && location.getGround().getDisplayChar() == '+') {
            location.setGround(new Sapling());
        }
    }

    /**
     * Getter method for jump successRate
     *
     * @return Wall success rate when player want to jump
     */
    public int getSuccessRate() {
		return 90;
	}

    /**
     * Getter method for jump fallDamage
     *
     * @return Wall fall damage when player failed to jump
     */
	public int getFallDamage() {
		return 10;
	}

    /**
     * Getter method for the high ground name
     *
     * @return high ground name
     */
    public String getName() {
        return "Sprout";
    }
}
