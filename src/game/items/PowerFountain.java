package game.items;

import edu.monash.fit2099.engine.actors.Actor;
import game.Status;

/**
 * class representing the power fountain
 */
public class PowerFountain extends Fountains{

    /***
     * Constructor.
     */
    public PowerFountain() {
        super("power fountain",'P',false);
        this.addCapability(Status.INDMG);

    }

    /**
     * method to check whether the fountain is empty
     * @return boolean to determine whether the fountain is empty
     */
    @Override
    public boolean useFound() {
        if (this.getCapacity()>0){
            return true;
        }else return false;


    }



}
