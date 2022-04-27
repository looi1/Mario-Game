package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.displays.Menu;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;

import java.util.*;
/**
 * Class representing the Player.
 */
public class Player extends Actor implements Resettable  {

	private final Menu menu = new Menu();
	private int marker = 0;
	private int initialHp;
	private int powerStarTicker = 0;

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

		// check at every turn if certain Status should be removed.
		expireStatus();

		// return/print the console menu
		return menu.showMenu(this, actions, display);

	}

	@Override
	public char getDisplayChar(){
		return this.hasCapability(Status.TALL) ? Character.toUpperCase(super.getDisplayChar()): super.getDisplayChar();
	}

	@Override
	public void resetInstance(Actor actor, GameMap map) {

		for(int i = 0; i<this.capabilitiesList().size();i++){

			this.removeCapability(this.capabilitiesList().get(i));

		}
		this.resetMaxHp(100); //heal to maximum
	}
	/**
	 * Gets the current Hp of Player as int by parsing Actor.printHp()
	 * @return int currentHp
	 */
	private int getCurrentHp() {
		String hpString = this.printHp();
		String newHpString = "";
		for (int i = 1; i < hpString.indexOf('/'); i++) {
			newHpString += hpString.charAt(i);
		}
		return Integer.valueOf(newHpString);
	}

	public void addSuperMushroomEffect() {
		this.addCapability(Status.SUPERMUSHROOM);
		initialHp = getCurrentHp();
		
		
	}

	public void addPowerStarEffect() {
		this.addCapability(Status.POWERSTAR);
	}

	public void expireStatus() {

		// SUPERMUSHROOM effect
		if (this.capabilitiesList().contains(Status.SUPERMUSHROOM)) {
			if (getCurrentHp() < initialHp) {
				this.removeCapability(Status.SUPERMUSHROOM);
			}
		}

		// POWERSTAR effect
		if (powerStarTicker <= 0) {
			
		}
	}

}
