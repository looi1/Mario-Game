package game.ground;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Ground;
import game.Status;
import game.reset.Resettable;

import java.nio.file.StandardCopyOption;
import java.util.Random;

/**
 * A Tree class that represents Ground and implements Resettable
 *
 * @see edu.monash.fit2099.engine.positions.Ground
 * @see game.reset.Resettable
 */
public abstract class Tree extends Ground implements Resettable {
    /**
     * instantiate a new Random class randnum
     */
    private final Random randnum = new Random();

    /**
     * Constructor.
     *
     * @param displayChar char to display in the Game Map
     */
    public Tree(char displayChar) {
        super(displayChar);
        registerInstance();
    }

    /**
     * Allows any classes that use this interface to reset abilities, attributes, and/or items.
     *
     * @param actor the actor to reset
     * @param map the Game Map that needs to be reset
     */
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

    /**
     * Returns true if an Actor can enter this location.
     *
     * Actors can enter a location if the terrain is passable and there isn't an Actor there already.
     * Game rule. One actor per location.
     * @param actor the Actor who might be moving
     * @return true if the Actor can enter this location
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        return actor.hasCapability(Status.POWERSTAR) || actor.hasCapability(Status.ENTER_HIGH_GROUND);
    }
}
