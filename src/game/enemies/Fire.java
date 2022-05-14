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

    @Override
    public void tick(Location location) {
        if (lifeSpan <= 0) {
            location.removeItem(this);
        } else {
            lifeSpan -= 1;
        }
    }

    /*@Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        return null;
    }

    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        return null;
    }

    //@Override
    //public void tick(Location location){
        //if(this.lifeSpan==0){
            //location.removeItem(this);
        //}else{
            //this.lifeSpan-=1;
        //}
    @Override
    public IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(20, "burn");
    }

    @Override
    public void addBehaviour(int priority, Behaviour behave) {

    }*/
}
