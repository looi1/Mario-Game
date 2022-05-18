package game.ground;

import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
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
        int x = location.x();
        int y = location.y();
        GameMap map = location.map();

        int high = 101;
        int low = 0;

        int high2 = 3;
        int low2 = -1;
        int randomX = r.nextInt((high2 - low2) + low2) - 1;
        int randomY = r.nextInt((high2 - low2) + low2) - 1;

        if (location.getActor() != null) {
            if (location.getActor().hasCapability(Status.POWERSTAR)) {
                location.setGround(new Dirt());
                location.addItem(new Coin(5));
            }
        }


        if (matureAge >= 0 && matureAge % 5 == 0 && location.getGround().getDisplayChar() == 'T') {
            // if (randomX != 0 && randomY != 0){
            //     if (map.at((randomX + x), (randomY + y)).getGround().getDisplayChar() == '.'){
            //         map.at((randomX + x), (randomY + y)).setGround(new Sprout());
            //     }
            // }
            for (Exit exit: location.getExits()) {
                Location loc = exit.getDestination();
                if (loc.getGround().getDisplayChar() == '.') {
                    loc.setGround(new Sprout());
                }
            }
        }

        int random = r.nextInt((high - low) + low);

        if(random>spawnRateKoopa && random<=spawnRateFKoopa && location.getActor() == null && location.getGround().getDisplayChar() == 'T'){
            location.addActor(new FlyingKoopa());
        }
        else if (random <= spawnRateKoopa && location.getActor() == null && location.getGround().getDisplayChar() == 'T'){
            location.addActor(new Koopa());

        }

        random = r.nextInt((high - low) + low);
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
