package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.actions.AttackAction;
import game.actions.RescueAction;
import game.behaviours.AttackBehaviour;
import game.behaviours.Behaviour;

import java.util.HashMap;
import java.util.Map;

public class PrincessPeach extends Actor {
    private final Map<Integer, Behaviour> behaviours = new HashMap<>(); // priority, behaviour

    public PrincessPeach() {
        super("princess peach", 'P', 100);
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
        if (otherActor.hasCapability(Status.UNLOCKABLE)) {
            actions.add(new RescueAction());
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
    public Action playTurn(ActionList actionList, Action action, GameMap gameMap, Display display) {
        return new DoNothingAction();
    }


}



