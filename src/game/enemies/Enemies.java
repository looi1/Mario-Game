package game.enemies;

import edu.monash.fit2099.engine.actors.Actor;
import game.behaviours.Behaviour;

/**
 * abstract class representing all the enemies( Goomba and Koopa)
 */
public abstract class Enemies extends Actor {

    /**
     * Constructor
     * @param name name of the enemy
     * @param displayChar display character of the enemy
     * @param hitPoints hitpoints of the enemy
     */
    public Enemies(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
    }

    /**
     * abstract method to add behaviour of the enemy
     * @param priority prority of the behaviour
     * @param behave the behaviour
     */
public abstract void addBehaviour(int priority, Behaviour behave);


    /**
     * method to get the hp of enemies
     * @return the maximum hp of enemies
     */
    public int getHp(){
        return this.getMaxHp();
    }
}
