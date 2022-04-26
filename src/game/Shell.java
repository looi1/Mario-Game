package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

public class Shell extends Actor {


    public Shell() {
        super("Shell", 'D', 50);
        this.addItemToInventory(new SuperMushroom());
    }

    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        return new DoNothingAction();
    }
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = new ActionList();

        if(otherActor.hasCapability(Status.BREAK_SHELL)){
            actions.add(new BreakShellAction(this));}
        return actions;
    }

    public String broken(Actor actor, Location location){
        String result = "";

        //location.map().removeActor(actor);
        //location.addItem(new SuperMushroom());
        result+= actor + " broken a "+ this;

        return result;
    }
}

