package game.ground;

import edu.monash.fit2099.engine.positions.Location;
import game.Status;
import game.items.Coin;

import java.util.Random;

/**
 * A Sprout class that represents a Tree and implements Jumpable
 *
 * @see edu.monash.fit2099.engine.positions.Ground
 */
public class Sapling extends Tree implements Jumpable {
    /**
     * sapling age that adds each turn
     */
    private int saplingAge = 0;

    /**
     * instantiate a new Random class r
     */
    private final Random r = new Random();

    /**
     * Constructor
     */
    public Sapling(){
        super('t');
    }

    /**
     * Sapling can also experience the joy of time.
     * @param location The location of the Sapling
     */
    @Override
    public void tick(Location location) {
        super.tick(location);
        saplingAge += 1;
        int spawnRateCoin = 10;
        
        int high = 101;
        int low = 0;
        int random = r.nextInt((high - low) + low);

        if (location.getActor() != null) {
            if (location.getActor().hasCapability(Status.POWERSTAR)) {
                location.setGround(new Dirt());
                location.addItem(new Coin(5));
            }
        }

        if (random <= spawnRateCoin && location.getGround().getDisplayChar() == 't'){
            location.addItem(new Coin(20));
        }

        if (saplingAge == 10 && location.getGround().getDisplayChar() == 't') {
            location.setGround(new Mature());
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
        return "Sapling";
    }
}
