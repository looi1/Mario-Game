package game;

import edu.monash.fit2099.engine.weapons.WeaponItem;

public class Wrench extends WeaponItem implements SellableItem{

    private int price = 200;

    public Wrench() {
        super("Wrench",'W',50,"wrench",50);
    }

    public int getPrice() {
        return price;
        super("wrench",'W',50,"wrench",50);
        this.addCapability(Status.BREAK_SHELL);
    }

}
