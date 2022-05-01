package game.ground;

import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.Status;
import game.enemies.Goomba;
import game.items.Coin;

import java.util.Random;

public class Sprout extends Tree {
    private int sproutAge = 0;
    private final Random r = new Random();

    public Sprout(){
        super('+');
    }

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

    /*@Override
    public void resetInstance(Actor actor, GameMap map) {
        if(r.nextInt(100)<=50){
            int minX = map.getXRange().min();
            int maxX = map.getXRange().max();

            int minY = map.getYRange().min();
            int maxY = map.getYRange().max();

            for(int i=0; i<(minX+maxX);i++){

                for(int j=0 ; j<(minY+maxY);j++){

                    if (map.at(i,j).getGround()==this){
                        map.at(i,j).setGround(new Dirt());
                    }
                }
            }
        }
    }*/

}
