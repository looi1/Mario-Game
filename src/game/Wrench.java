package game;

import edu.monash.fit2099.engine.weapons.WeaponItem;

public class Wrench extends WeaponItem implements SellableItem{

    public Wrench() {
        super("Wrench",'W',50,"wrench",50);
        this.addCapability(Status.BREAK_SHELL);
    }

    public int getPrice() {
        return 200;
    }

}
