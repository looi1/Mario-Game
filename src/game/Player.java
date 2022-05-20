package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.displays.Menu;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.actions.AttackAction;
import game.actions.ResetAction;
import game.behaviours.JumpBehaviour;
import game.items.Fountains;
import game.reset.Resettable;
import game.enemies.*;
import edu.monash.fit2099.engine.positions.Exit;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing the Player that represent Actor and implements Resettable
 *
 * @see edu.monash.fit2099.engine.actors.Actor
 * @see game.reset.Resettable
 */
public class Player extends Actor implements Resettable {

	private final Menu menu = new Menu();
	private int powerStarEffectTicker;
	private int armor = 0;
	private int remainingAura;
	private int freezesLeft;
	private Yoshi yoshi;
	private int damage;

	/**
	 * Constructor.
	 *
	 * @param name        Name to call the player in the UI
	 * @param displayChar Character to represent the player in the UI
	 * @param hitPoints   Player's starting number of hit points
	 */
	public Player(String name, char displayChar, int hitPoints) {
		super(name, displayChar, hitPoints);
		this.addCapability(Status.HOSTILE_TO_ENEMY);
		this.registerInstance();
		this.damage = 5;

	}

	/**
	 * Method to adopt Yoshi
	 *
	 * @param yoshi the Yoshi that the Player wants to adopt
	 */
	public void adoptYoshi(Yoshi yoshi) {
		this.yoshi = yoshi;
	}

	/**
	 * Select and return an action to perform on the current turn.
	 *
	 * @param actions    collection of possible Actions for this Actor
	 * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
	 * @param map        the map containing the Actor
	 * @param display    the I/O object to which messages may be written
	 * @return the Action to be performed
	 */
	@Override
	public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {

		Location locationPlayer = map.locationOf(this);
		for (int i = 0; i < locationPlayer.getItems().size(); i++) {
			if (locationPlayer.getItems().get(i).getDisplayChar() == 'v') {
				int dmg = locationPlayer.getItems().get(i).asWeapon().damage();
				this.hurt(dmg);
				if (!this.isConscious()) {
					map.removeActor(this);
					display.println(this + " is killed.");
					return new DoNothingAction();

				} else {
					display.println(this + " " + locationPlayer.getItems().get(i).asWeapon().verb() + " with " + dmg + " damages!");
				}
			}
		}

		// Use effects at every turn, while checking for conditions of effect removal
		effects(lastAction, map, display);

		// Reset
		capacityFountain(display,map);

		// Handle multi-turn Actions
		ResetAction reset = ResetAction.getInstance();
		if (reset != null) {
			actions.add(reset);
		}

		// Handle multi-turn actions
		if (lastAction.getNextAction() != null)
			return lastAction.getNextAction();

		// Yoshi helps attack after Mario attacks
		if (lastAction instanceof AttackAction) {
			AttackAction lastAttack = (AttackAction) lastAction;
			yoshi.attackEnemy(lastAttack.getEnemy());
		} else {
			yoshi.disengage();
		}

		// Add jump if no PowerStar status
		if (!(this.hasCapability(Status.POWERSTAR))) {
			actions.add(addJump(map)); // if the player don't have the POWERSTAR Status, then add jump action
		}

		// Print health and status
		String ground;
		Location here = map.locationOf(this);

		if (here.getGround().getDisplayChar() == '#') {
			ground = "Wall";
		} else if (here.getGround().getDisplayChar() == '+') {
			ground = "Sprout";
		} else if (here.getGround().getDisplayChar() == 't') {
			ground = "Sapling";
		} else if (here.getGround().getDisplayChar() == 'T') {
			ground = "Mature";
		} else if (here.getGround().getDisplayChar() == '_') {
			ground = "Floor";
		} else {
			ground = "Dirt";
		}

		display.println("Mario" + this.printHp() + " at " + ground + "(" + map.locationOf(this).x() + ", "
				+ map.locationOf(this).y() + ")");
		return menu.showMenu(this, actions, display);
	}

	/**
	 * Do some damage to the current Actor.
	 *
	 * If the Actor's hitpoints go down to zero, it will be knocked out.
	 *
	 * @param points number of hitpoints to deduct.
	 */
	@Override
	public void hurt(int points) {
		if (getCurrentHp() <= 40 && yoshi.isConscious()) {
			yoshi.hurt(points);
		} else {
			super.hurt(points - armor);
			removeEffect(Status.SUPERMUSHROOM);
		}
	}

	/**
	 * A getter method to get the Player current HP
	 *
	 * @return Player current HP
	 */
	private int getCurrentHp() {
		String hpString = printHp().split("/")[0];
		hpString = hpString.substring(1);
		return Integer.parseInt(hpString);
	}

	/**
	 * Method to add effect to the Player
	 *
	 * @param effect that wants to be added
	 */
	public void addEffect(Enum<?> effect) {
		System.out.println("Add effect : " + effect);
		if (effect.equals(Status.POWERSTAR)) {
			this.addCapability(Status.POWERSTAR);
			powerStarEffectTicker = 10;
			this.heal(200);
			System.out.println("10 turns remaining");
		}

		if (effect.equals(Status.SUPERMUSHROOM)) {
			this.addCapability(Status.SUPERMUSHROOM);
			this.increaseMaxHp(50);
			this.setDisplayChar('M');
		}

		if (effect.equals(Status.AURA)) {
			this.addCapability(Status.AURA);
			this.armor += 15;
			this.remainingAura = 80;
		}

		if (effect.equals(Status.FREEZE)) {
			this.addCapability(Status.FREEZE);
			freezesLeft = 10;
		}
	}

	/**
	 * Method to check if the Player has any effect
	 *
	 * @param lastAction Player last action
	 * @param map the map that the Player is in
	 * @param display to display the message
	 */
	private void effects(Action lastAction, GameMap map, Display display) {

		if (this.hasCapability(Status.POWERSTAR)) {
			if (powerStarEffectTicker <= 0) {
				removeEffect(Status.POWERSTAR);
			} else {
				display.println("Mario is INVINCIBLE!");
				powerStarEffectTicker -= 1;
			}
		}

		if (this.hasCapability(Status.AURA)) {
			int auraDamage = 15;
			List<Exit> exits = map.locationOf(this).getExits();
			for (Exit exit : exits) {
				if (exit.getDestination().getActor() instanceof Enemies) {
					Enemies enemy = (Enemies) exit.getDestination().getActor();
					enemy.hurt(auraDamage);
					display.println("Aura hurts " + enemy + " for " + auraDamage + " damage!");
					if (!enemy.isConscious()) {
						map.removeActor(enemy);
					}
					remainingAura -= auraDamage;
				}
				if (remainingAura <= 0) {
					removeEffect(Status.AURA);
					display.println("Aura depleted");
				}
			}
		}

		if (this.hasCapability(Status.FREEZE)) {
			if (lastAction instanceof AttackAction) {
				AttackAction lastAttack = (AttackAction) lastAction;
				lastAttack.getEnemy().removeBehaviour(1);
				freezesLeft -= 1;
			}
			if (freezesLeft <= 0) {
				removeEffect(Status.FREEZE);
			}
		}

	}

	/**
	 * Method to remove the effect on the Player
	 *
	 * @param effect the effect that wants to be removed
	 */
	private void removeEffect(Enum<?> effect) {

		if (effect.equals(Status.POWERSTAR)) {
			this.removeCapability(Status.POWERSTAR);
		}

		if (effect.equals(Status.SUPERMUSHROOM)) {
			this.removeCapability(Status.SUPERMUSHROOM);
			this.setDisplayChar('m');
		}

		if (effect.equals(Status.AURA)) {
			this.addCapability(Status.AURA);
			this.armor -= 10;
		}

		if (effect.equals(Status.FREEZE)) {
			this.removeCapability(Status.FREEZE);
		}
	}

	/**
	 * Method to display the fountain capacity
	 *
	 * @param display message to display
	 * @param map map where the fountain is located
	 */
	private void capacityFountain(Display display, GameMap map){
		Location locate = map.locationOf(this);
		String text="";

		for (int i = 0; i<locate.getItems().size();i++) {
			if (locate.getItems().get(i).hasCapability(Status.HEAL) && ((Fountains)locate.getItems().get(i) ).getCapacity()>0){
				text +="Health Fountain " + ((Fountains)locate.getItems().get(i) ).getCapacity()+"/10";
			}
			else if (locate.getItems().get(i).hasCapability(Status.HEAL) && ((Fountains)locate.getItems().get(i) ).getCapacity()<=0) {
				//this.healthFoundTicker += 1;
				text +="Health Fountain " + ((Fountains)locate.getItems().get(i) ).getCapacity()+"/10";
				// if (this.healthFoundTicker % 5 == 0) {
					//((Fountains) locate.getItems().get(i)).setCapacity();
				//}
			}

			if (locate.getItems().get(i).hasCapability(Status.INDMG )  && ((Fountains)locate.getItems().get(i) ).getCapacity()>0){
				text+= "Power Fountain " + ((Fountains)locate.getItems().get(i) ).getCapacity()+"/10";
			}
			else if (locate.getItems().get(i).hasCapability(Status.INDMG) && ((Fountains)locate.getItems().get(i) ).getCapacity()<=0){
				//this.powerFoundTicker+=1;
				text +="Power Fountain " + ((Fountains)locate.getItems().get(i) ).getCapacity()+"/10";
				//if (this.powerFoundTicker % 5 == 0) {
					//((Fountains) locate.getItems().get(i)).setCapacity();
				//}
			}
		}
		display.println(text);
	}


	/**
	 * Check the surrounding Ground around the Player if it is a high ground
	 * Add the jump action to the actions List if the player doesn't have the
	 * POWERSTAR effect
	 *
	 * @param map the Game Map containing the Player
	 * @return List of jump action
	 */
	public List<Action> addJump(GameMap map) {
		ArrayList<Action> actions = new ArrayList<>();

		List<Character> highGrounds = new ArrayList<>();
		highGrounds.add('#');
		highGrounds.add('+');
		highGrounds.add('t');
		highGrounds.add('T');
		highGrounds.add('C');

		for (Exit exit: map.locationOf(this).getExits()) {
			Location loc = exit.getDestination();
			if (highGrounds.contains(loc.getDisplayChar()) && !this.hasCapability(Status.POWERSTAR)) {
				actions.add(new JumpBehaviour(loc.getGround(), loc));
			}
		}
		return actions;
	}

	@Override
	public char getDisplayChar() {
		return this.hasCapability(Status.TALL) ? Character.toUpperCase(super.getDisplayChar()) : super.getDisplayChar();
	}

	@Override
	public void resetInstance(Actor actor, GameMap map) {

		for (int i = 0; i < this.capabilitiesList().size(); i++) {

			this.removeCapability(this.capabilitiesList().get(i));

		}
		this.resetMaxHp(1000); // heal to maximum
	}

	/**
	 * A getter method to get the Yoshi object
	 *
	 * @return yoshi
	 */
	public Yoshi getYoshi() {
		return yoshi;
	}

	@Override
	public IntrinsicWeapon getIntrinsicWeapon() {
		return new IntrinsicWeapon(this.damage, " punch ");

	}

	/**
	 * Method to increase the Player base damage
	 *
	 * @param newDmg how much damage that wants to be increased by
	 */
	public void setDamage(int newDmg){
		this.damage+=newDmg;
	}
}