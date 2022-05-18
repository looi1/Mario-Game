package game.enemies;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import edu.monash.fit2099.engine.weapons.Weapon;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.Status;
import game.actions.AttackAction;
import game.behaviours.AttackBehaviour;
import game.behaviours.Behaviour;

/**
 * class representing bowser's attack Fire
 */
public class Fire extends WeaponItem {
    private int lifeSpan = 3;
    private Actor actor;
    public Fire() {
        super("Fire",'v',20,"burn",100);
        this.addCapability(Status.FIREDMG);
        this.togglePortability();
        //this.actor = newActor;
        //this.addAction(Attack);
        //this.addAction(new AttackBehaviour(actor));

    }

    /**
     * a method to remove the fire from map based on turn
     * @param location
     */
    @Override
    public void tick(Location location) {
        if (lifeSpan <= 0) {
            location.removeItem(this);
        } else {
            lifeSpan -= 1;
        }
    }

}
