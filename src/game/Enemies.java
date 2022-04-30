package game;

import edu.monash.fit2099.engine.actors.Actor;

public abstract class Enemies extends Actor {
    public Enemies(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
    }

    public abstract void addBehaviour(int priority, Behaviour behave);




    public int getHp(){
        return this.getMaxHp();
    }
}
