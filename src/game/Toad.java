package game;

import edu.monash.fit2099.engine.actors.Actor;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.SpeakAction;
import game.actions.*;
import game.items.*;


/**
 * Class representing the Toad.
 */
public class Toad extends Actor{

	/**
	 * Constructor
	 */
    public Toad() {
		super("Toad", 'O', 1);
	}

	/**
	 * Add a BuyAction for each of the sold items, and a SpeakAction.
	 * 
	 */
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
		ActionList actions = new ActionList();
		if(otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
			PowerStar powerStar = new PowerStar();
			powerStar.togglePortability();
			SuperMushroom superMushroom = new SuperMushroom();
			superMushroom.togglePortability();
			actions.add(new BuyAction(powerStar));
			actions.add(new BuyAction(superMushroom));
			actions.add(new BuyAction(new Wrench()));
			actions.add(new SpeakAction(false));
		}
		return actions;
	}

	public Action playTurn(ActionList actionList, Action action, GameMap gameMap, Display display) {
		return new DoNothingAction();
	}

	




}

