package game;

import edu.monash.fit2099.engine.weapons.WeaponItem;

public class Wrench extends WeaponItem {

    public Wrench() {
        super("wrench",'W',50,"wrench",50);
        this.addCapability(Status.BREAK_SHELL);
    }
}
