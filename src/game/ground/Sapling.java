package game.ground;

import edu.monash.fit2099.engine.positions.Location;
import game.Status;
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
