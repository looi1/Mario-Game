package game.ground;

import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;
import game.Status;
import game.enemies.FlyingKoopa;
import game.enemies.Koopa;
import game.items.Coin;

import java.util.Random;

/**
 * A Mature class that represents Tree and implements Jumpable
 *
 * @see edu.monash.fit2099.engine.positions.Ground
 */
public class Mature extends Tree implements Jumpable {
    /**
     * mature age that adds each turn
     */
    private int matureAge = 0;

    /**
     * instantiate a new Random class r
     */
    private final Random r = new Random();

    /**
     * Constructor
     */
    public Mature(){
        super('T');
    }

    /**
     * Mature can also experience the joy of time.
     * @param location The location of the Mature
     */
    @Override
    public void tick(Location location) {
        super.tick(location);
        matureAge += 1;
        int spawnRateKoopa = 15;
        int spawnRateFKoopa = 50;
        int witherRate = 20;

        int high = 101;
        int low = 0;

        // Destroy the Mature and drop a Coin($5) if the Player walks into it while having Powerstar effect
        if (location.getActor() != null) {
            if (location.getActor().hasCapability(Status.POWERSTAR)) {
                location.setGround(new Dirt());
                location.addItem(new Coin(5));
            }
        }

        // Mature will grow a new Sprout in every 5 turns
        if (matureAge >= 0 && matureAge % 5 == 0 && location.getGround().getDisplayChar() == 'T') {
            for (Exit exit: location.getExits()) {
                Location loc = exit.getDestination();
                if (loc.getGround().getDisplayChar() == '.') {
                    loc.setGround(new Sprout());
                    break;
                }
            }
        }

        int random = r.nextInt((high - low) + low);

        // Mature will spawn either Koopa or Flying Koopa depending on the spawn rate
        if (random <= spawnRateKoopa && location.getActor() == null && location.getGround().getDisplayChar() == 'T'){
            int random2 = r.nextInt((high - low) + low);
            if (random2 <= spawnRateFKoopa) {
                location.addActor(new FlyingKoopa());
            }
            else {
                location.addActor(new Koopa());
            }
        }

        random = r.nextInt((high - low) + low);

        // Mature has 20% rate to wither and die
        if (random <= witherRate && location.getGround().getDisplayChar() == 'T'){
            location.setGround(new Dirt());
        }
    }

    /**
     * Getter method for jump successRate
     *
     * @return Wall success rate when player want to jump
     */
    public int getSuccessRate() {
		return 70;
	}

    /**
     * Getter method for jump fallDamage
     *
     * @return Wall fall damage when player failed to jump
     */
	public int getFallDamage() {
		return 30;
	}

    /**
     * Getter method for the high ground name
     *
     * @return high ground name
     */
    public String getName() {
        return "Mature";
    }
}
