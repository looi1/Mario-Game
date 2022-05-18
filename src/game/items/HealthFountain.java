package game.items;

import edu.monash.fit2099.engine.actors.Actor;
import game.Status;

/**
 * class representing the health fountain
 */
public class HealthFountain extends Fountains{
    /***
     * Constructor.
     */
    public HealthFountain() {
    super("health fountain", 'H', false);
    this.addCapability(Status.HEAL);

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
