package game.items;

import game.Status;

/**
 * A HealthFountain class that produces the healing magical water which represents Fountains
 *
 * @see game.items.Fountains
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
     * Method to check whether the fountain is empty
     *
     * @return boolean to determine whether the fountain is empty
     */
    @Override
    public boolean useFound() {
        return this.getCapacity() > 0;
    }

    /**
     * A getter method to get the fountain/water type
     *
     * @return the fountain/water type
     */
    @Override
    public String getName() {
        return "Health water";
    }
}
