package game.ground;

import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.enemies.Koopa;

import java.util.Random;

public class Mature extends Tree implements Jumpable {
    private int matureAge = 0;
    private final Random r = new Random();

    public Mature(){
        super('T');
    }

    @Override
    public void tick(Location location) {
        super.tick(location);
        matureAge += 1;
        int spawnRateKoopa = 15;
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

        //System.out.println("Here");
        if (matureAge >= 0 && matureAge % 5 == 0) {
            //System.out.println("5 turns");
            if (randomX != 0 && randomY != 0){
                //System.out.println(map.at((randomX + x), (randomY + y)).getGround().getDisplayChar() == '.');
                if (map.at((randomX + x), (randomY + y)).getGround().getDisplayChar() == '.'){
                    map.at((randomX + x), (randomY + y)).setGround(new Sprout());
                }
            }
        }

        int random = r.nextInt((high - low) + low);
        if (random <= spawnRateKoopa && location.getActor() == null){
            location.addActor(new Koopa());
        }

        random = r.nextInt((high - low) + low);
        if (random <= witherRate){
            location.setGround(new Dirt());
        }
    }

    public int getSuccessRate() {
		return 70;
	}

	public int getFallDamage() {
		return 30;
	}

    public String getName() {
        return "Mature";
    }
}
