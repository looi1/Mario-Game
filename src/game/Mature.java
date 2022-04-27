package game;

import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

import java.util.Random;

public class Mature extends Tree{
    private int matureAge = 0;

    public Mature(){
        super('T');
    }

    @Override
    public void tick(Location location) {
        super.tick(location);
        matureAge += 1;
        int spawnRateKoopa = 15;
        int witherRate = 20;

        Random r = new Random();
        int high = 101;
        int low = 0;

        int random = r.nextInt((high - low) + low);
        if (random <= spawnRateKoopa){
            location.addActor(new Koopa());
        }

        int x = location.x();
        int y = location.y();
        GameMap map = location.map();

        int high2 = 3;
        int low2 = -1;
        int randomX = r.nextInt((high2 - low2) + low2) - 1;
        int randomY = r.nextInt((high2 - low2) + low2) - 1;

        if (matureAge >= 0 && matureAge % 5 == 0) {
            if (randomX != 0 && randomY != 0){
                if (map.at((randomX + x), (randomY + y)).getGround().equals(new Dirt())){
                    map.at((randomX + x), (randomY + y)).setGround(new Sprout());
                }
            }
        }

        random = r.nextInt((high - low) + low);
        if (random <= witherRate){
            location.setGround(new Dirt());
        }
    }
}
