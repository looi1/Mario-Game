package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.displays.Menu;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.AttackAction;
import game.actions.ResetAction;
import game.behaviours.AttackBehaviour;
import game.behaviours.FireAttackBehaviour;
import game.behaviours.FrozenBehaviour;
import game.behaviours.JumpBehaviour;
import game.reset.Resettable;
import edu.monash.fit2099.engine.*;
import game.enemies.*;
import game.items.FreezePotion;
import edu.monash.fit2099.engine.positions.Exit;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing the Player.
 */
public class Player extends Actor implements Resettable {

	private final Menu menu = new Menu();
	private int powerStarEffectTicker;
	private int armor = 0;
	private int remainingAura;
	private int freezesLeft;
	private Yoshi yoshi;

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

	}

	public void adoptYoshi(Yoshi yoshi) {
		this.yoshi = yoshi;
	}

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
				} else {
					display.println(this + " " + locationPlayer.getItems().get(i).asWeapon().verb() + " with " + dmg
							+ " damages!");
				}
			}
		}

		// use effects at every turn, while checking for conditions of effect removal
		effects(lastAction, map, display);

		// Reset
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

	@Override
	public void hurt(int points) {
		if (getCurrentHp() <= 40 && yoshi.isConscious()) {
			yoshi.hurt(points);
		} else {
			super.hurt(points - armor);
			removeEffect(Status.SUPERMUSHROOM);
		}
	}

	private int getCurrentHp() {
		String hpString = printHp().split("/")[0];
		hpString = hpString.substring(1);
		return Integer.valueOf(hpString);
	}

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
				};
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
	 * Check the surrounding Ground around the Player if it is a high ground
	 * Add the jump action to the actions List if the player doesn't have the
	 * POWERSTAR effect
	 *
	 * @param map the Game Map containing the Player
	 * @return List of jump action
	 */
	public List<Action> addJump(GameMap map) {
		ArrayList<Action> actions = new ArrayList<Action>();

		int x = map.locationOf(this).x();
		int y = map.locationOf(this).y();

		List<Character> highGrounds = new ArrayList<Character>();
		highGrounds.add('#');
		highGrounds.add('+');
		highGrounds.add('t');
		highGrounds.add('T');

		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (!(x + i == x && y + j == y)) {
					if (highGrounds.contains(map.at((x + i), (y + j)).getDisplayChar()) && x + i >= 0 && y + j >= 0) {
						if (this.hasCapability(Status.POWERSTAR)) {
							break;
						} else {
							Location highGround = map.at((x + i), (y + j));
							actions.add(new JumpBehaviour(highGround.getGround(), highGround));
						}
					}
				}
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
		this.resetMaxHp(100); // heal to maximum
	}

	public Yoshi getYoshi() {
		return yoshi;
	}

}