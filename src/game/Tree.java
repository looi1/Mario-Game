package game;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;

import java.util.Random;

public class Tree extends Ground implements Resettable {
    private Random randnum = new Random();

    /**
     * Constructor.
     *
     */
    public Tree() {
        super('+');
        registerInstance();
    }




    @Override
    public void resetInstance(Actor actor, GameMap map) {
        if(randnum.nextInt(100)<=50){


        }


        }

    }

