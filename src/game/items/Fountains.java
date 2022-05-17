package game.items;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.DrinkAction;

public abstract class Fountains extends Item {

    private int capacity;

    private int ticker;
    /***
     * Constructor.
     *  @param name the name of this Item
     * @param displayChar the character to use to represent this item if it is on the ground
     * @param portable true if and only if the Item can be picked up
     */
    public Fountains(String name,char displayChar,Boolean portable) {
        super(name, displayChar, portable);
        super.addAction(new DrinkAction(this));
        this.capacity = 10;
        this.ticker = 0;
    }

    public abstract boolean useFound();

    public void drink(){
        this.capacity-=5;
    }

    public int getCapacity(){
        return this.capacity;
    }
    public void setCapacity(){
        this.capacity = 10;
    }

    @Override
    public void tick(Location location) {
        if (this.getCapacity()==0) {
            this.ticker+=1;
            if(this.ticker % 6==0 ){
                this.setCapacity();
        }

        }
    }


}
