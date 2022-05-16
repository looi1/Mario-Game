package game.items;

import edu.monash.fit2099.engine.actors.Actor;
import game.Status;

public class PowerFountain extends Fountains{

    /***
     * Constructor.
     * @param name the name of this Item
     * @param displayChar the character to use to represent this item if it is on the ground
     * @param portable true if and only if the Item can be picked up
     */
    public PowerFountain(String name, char displayChar, Boolean portable) {

        super(name, displayChar, portable);
        this.addCapability(Status.INDMG);

    }

    @Override
    public boolean useFound() {
        if (this.getCapacity()>0){
            return true;
        }else return false;


    }



}
