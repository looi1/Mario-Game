package game.enemies;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.Status;
import game.actions.AttackAction;
import game.behaviours.AttackBehaviour;
import game.behaviours.Behaviour;
import game.behaviours.DrinkBehaviour;
import game.reset.Resettable;

import java.util.HashMap;
import java.util.Map;

public class PiranhaPlant extends Enemies implements Resettable {
    private final Map<Integer, Behaviour> behaviours = new HashMap<>(); // priority, behaviour
    private int damage;
    public PiranhaPlant(){
        super("PiranhaPlant",'Y',150);
        this.damage = 90;
        this.behaviours.put(9,new DrinkBehaviour());
        this.registerInstance();
    }
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = new ActionList();
        // it can be attacked only by the HOSTILE opponent, and this action will not attack the HOSTILE enemy back.
        if (otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
            actions.add(new AttackAction(this, direction));
            this.behaviours.put(9,new AttackBehaviour(otherActor));
        }


        //this.behaviours.put(9,new AttackBehaviour(otherActor));
        return actions;
    }

    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        for (Behaviour Behaviour : behaviours.values()) {
            Action action = Behaviour.getAction(this, map);
            if (action != null)
                return action;
        }
        return new DoNothingAction();

    }

    @Override
    public void addBehaviour(int priority, Behaviour behave) {this.behaviours.put(priority, behave);

    }
    @Override
    public IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(this.damage, "chomps");
    }

    @Override
    public void resetInstance(Actor actor, GameMap map) {
        this.heal(150);
        this.increaseMaxHp(50);


        }

    @Override
    public void setDamage(int newDamage) {
        this.damage += newDamage;

    }


}

