package game;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Ground;

import java.util.Random;

public abstract class Tree extends Ground implements Resettable {
    private final Random randnum = new Random();

    /**
     * Constructor.
     *
     */
    public Tree(char displayChar) {
        super(displayChar);
        registerInstance();
    }

    @Override
    public void resetInstance(Actor actor, GameMap map) {
        if(randnum.nextInt(100)<=50){
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
    }

    @Override
    public boolean canActorEnter(Actor actor) {
        return false;
    }
}
