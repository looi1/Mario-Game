package game.items;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.Status;
import game.actions.ConsumeAction;
import game.reset.Resettable;

import java.util.ArrayList;

/**
 * A Bottle class to store the magical waters that represents Item and implements Consumable and Resettable
 *
 * @see edu.monash.fit2099.engine.items.Item
 * @see game.items.Sellable
 * @see game.items.Consumable
 */
public class Bottle extends Item implements Consumable, Resettable {
    /**
     * An ArrayList that contains the magical waters and act as a Stack
     */
    private static ArrayList<Fountains> fountainList = new ArrayList <>();

    /***
     * Constructor.
     */
    public Bottle() {
        super("Bottle", 'b', false);
        super.addAction(new ConsumeAction(this));
        super.addCapability(Status.HAS_BOTTLE);
        this.registerInstance();
    }

    /**
     * Get the Status associated with the item
     *
     * @return Status
     */
    @Override
    public Enum<?> getConsumeStatus() {
        return null;
    }

    /**
     * Getter method for fountainList
     *
     * @return fountainList ArrayList that contains the magical waters
     */
    public static ArrayList<Fountains> getFountain() {
        return fountainList;
    }

    /**
     * Push method to push/add the magical waters from fountain to the ArrayList that will act as a Stack
     *
     * @param fountains the fountain that produce the magical water
     */
    public void push(Fountains fountains){
        fountainList.add(fountains);
    }

    @Override
    public void resetInstance(Actor actor, GameMap map) {
        for (int i = 0; i < fountainList.size(); i++){
            fountainList.remove(fountainList.size() - 1);
        }
        actor.removeItemFromInventory(this);
        actor.removeCapability(Status.HAS_BOTTLE);
    }
}
