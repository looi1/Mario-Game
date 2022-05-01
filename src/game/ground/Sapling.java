package game.ground;

import edu.monash.fit2099.engine.positions.Location;
import game.items.Coin;

import java.util.Random;

public class Sapling extends Tree implements Jumpable {
    private int saplingAge = 0;
    private final Random r = new Random();

    public Sapling(){
        super('t');
    }

    @Override
    public void tick(Location location) {
        super.tick(location);
        saplingAge += 1;
        int spawnRateCoin = 10;
        
        int high = 101;
        int low = 0;
        int random = r.nextInt((high - low) + low);

        if (random <= spawnRateCoin){
            location.addItem(new Coin(20));
        }

        if (saplingAge == 10) {
            location.setGround(new Mature());
        }
    }

    public int getSuccessRate() {
		return 80;
	}

	public int getFallDamage() {
		return 20;
	}

    public String getName() {
        return "Sapling";
    }
}
