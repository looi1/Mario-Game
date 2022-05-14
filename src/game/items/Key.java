package game.items;
import edu.monash.fit2099.engine.items.Item;
import game.Status;

public class Key extends Item {

    public Key(){
        super("key",'k',true);
        this.addCapability(Status.UNLOCKABLE);
    }
}