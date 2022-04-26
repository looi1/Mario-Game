package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.displays.Menu;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;

import java.security.DrbgParameters;
import java.util.*;
/**
 * Class representing the Player.
 */
public class Player extends Actor implements Resettable  {

	private final Menu menu = new Menu();
	private int marker = 0;

	/**
	 * Constructor.
	 *
	 * @param name        Name to call the player in the UI
	 * @param displayChar Character to represent the player in the UI
	 * @param hitPoints   Player's starting number of hitpoints
	 */
	public Player(String name, char displayChar, int hitPoints) {
		super(name, displayChar, hitPoints);
		this.addCapability(Status.HOSTILE_TO_ENEMY);
		this.registerInstance();

	}

	@Override
	public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
		// Handle multi-turn Actions
		ResetAction reset = ResetAction.getInstance();

		if (reset != null){
			actions.add(reset);}

		if (lastAction.getNextAction() != null)
			return lastAction.getNextAction();

		// return/print the console menu
		return menu.showMenu(this, actions, display);

	}

	@Override
	public char getDisplayChar(){
		return this.hasCapability(Status.TALL) ? Character.toUpperCase(super.getDisplayChar()): super.getDisplayChar();
	}

	@Override
	//ts
	//player attack by enemies
	public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
		ActionList actions = new ActionList();
		//actions.add(new ResetAction());
		// it can be attacked only by the HOSTILE opponent, and this action will not attack the HOSTILE enemy back.
		if(otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
			actions.add(new AttackAction(this,direction));
		}
		return actions;
	}

	@Override
	public void resetInstance(Actor actor, GameMap map) {

		for(int i = 0; i<this.capabilitiesList().size();i++){

			this.removeCapability(this.capabilitiesList().get(i));

		}
		this.resetMaxHp(100); //heal to maximum

	}

	// addsupermushroomeffect()
	/*
	initialHealth
	**/
}
