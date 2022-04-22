package game;

import edu.monash.fit2099.engine.actors.Actor;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.positions.GameMap;
import java.util.*;

/**
 * Class representing the Toad.
 */
public class Toad extends Actor{

	private ArrayList<SellableItems> shop = new ArrayList<SellableItems>();

    public Toad() {
		super("Toad", 'O', 1);
	}

    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
		ActionList actions = new ActionList();
		if(otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
			//actions.add(new TradeAction(shop));
			actions.add(new SpeakAction());
		}
		return actions;
	}

	public Action playTurn(ActionList actionList, Action action, GameMap gameMap, Display display) {
		return new DoNothingAction();
	}


}

