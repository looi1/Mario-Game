package game.items;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.DrinkAction;
import game.reset.Resettable;

/**
 * A Fountain class that produces the magical water which represents Item and impelemnts Resettable
 *
 * @see edu.monash.fit2099.engine.items.Item
 */
public abstract class Fountains extends Item implements Resettable {

    /**
     * The capacity of the fountain
     */
    private int capacity;

    /**
     * Ticker for the fountain to recharge the water after every turn if the fountain is exhausted
     */
    private int ticker;

    /***
     * Constructor.
     *
     * @param name the name of this Item
     * @param displayChar the character to use to represent this item if it is on the ground
     * @param portable true if and only if the Item can be picked up
     */
    public Fountains(String name,char displayChar,Boolean portable) {
        super(name, displayChar, portable);
        super.addAction(new DrinkAction(this));
        this.capacity = 10;
        this.ticker = 0;
        this.registerInstance();
    }

    /**
     * Method to check whether the fountain is empty
     *
     * @return boolean to determine whether the fountain is empty
     */
    public abstract boolean useFound();

    /**
     * A getter method to get the fountain/water type
     *
     * @return the fountain/water type
     */
    public abstract String getName();

    /**
     * Method to drink or refill magical water from the fountain
     * which reduce the water in the fountain
     */
    public void drink(){
        this.capacity-=5;
    }

    /**
     * A getter method to get the fountain capacity
     *
     * @return the fountain capacity
     */
    public int getCapacity(){
        return this.capacity;
    }

    /**
     * A setter method to set the fountain capacity
     */
    public void setCapacity(){
        this.capacity = 10;
    }

    /**
     * Inform an Item on the ground of the passage of time.
     * This method is called once per turn, if the item rests upon the ground.
     *
     * @param location The location of the ground on which we lie.
     */
    @Override
    public void tick(Location location) {
        if (this.getCapacity()==0) {
            this.ticker+=1;
            if(this.ticker % 6==0 ){
                this.setCapacity();
            }
        }
    }

    @Override
    public void resetInstance(Actor actor, GameMap map) {
        this.setCapacity();
    }
}
