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
import game.behaviours.WanderBehaviour;
import game.items.SuperMushroom;
import game.reset.Resettable;

import java.util.HashMap;
import java.util.Map;

public class FlyingKoopa extends Enemies implements Resettable {
    private final Map<Integer, Behaviour> behaviours = new HashMap<>(); // priority, behaviour

    public FlyingKoopa(){
        super("FlyingKoopa",'F',150);
        this.behaviours.put(10, new WanderBehaviour());
        this.addItemToInventory(new SuperMushroom());
        this.addCapability(Status.CANT_ENTER_FLOOR);
        this.addCapability(Status.HAS_SHELL);
        this.addCapability(Status.FLY);

        this.registerInstance();
    }
    /**
     * At the moment, we only make it can be attacked by Player.
     * You can do something else with this method.
     *
     * @param otherActor the Actor that might perform an action.
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return list of actions
     * @see Status#HOSTILE_TO_ENEMY
     */
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

    /**
     * Figure out what to do next.
     *
     * @see Actor#playTurn(ActionList, Action, GameMap, Display)
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {

        Location locationPlayer = map.locationOf(this);
        for(int i = 0; i<locationPlayer.getItems().size() ; i++) {
            if (locationPlayer.getItems().get(i).getDisplayChar() == 'v') {
                int dmg = locationPlayer.getItems().get(i).asWeapon().damage();
                this.hurt(dmg);
                if (!this.isConscious()) {
                    map.removeActor(this);
                    display.println(this + " is killed!");
                } else {
                    display.println(this + " " + locationPlayer.getItems().get(i).asWeapon().verb() + " with " + dmg + " damages!");

                }
            }
        }

        for (game.behaviours.Behaviour Behaviour : behaviours.values()) {
            Action action = Behaviour.getAction(this, map);
            if (action != null)
                return action;
        }
        return new DoNothingAction();
    }

    /**
     * method to add behaviour to koopa
     * @param priority prority of the behaviour
     * @param behave the behaviour
     */
    @Override
    public void addBehaviour(int priority, Behaviour behave) {
        this.behaviours.put(priority, behave);
    }

    /**
     * method to implement weapon Koopa used to attack player
     * @return
     */
    @Override
    public IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(30, "punches");
    }


    @Override
    public void resetInstance(Actor actor, GameMap map) {
        map.removeActor(this);


    }
}