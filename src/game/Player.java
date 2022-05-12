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
import game.behaviours.JumpBehaviour;
import game.reset.Resettable;

import java.util.ArrayList;
import java.util.List;


/**
 * Class representing the Player.
 */
public class Player extends Actor implements Resettable {

	private final Menu menu = new Menu();
	private int powerStarEffectTicker;
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
		for(int i = 0; i<locationPlayer.getItems().size() ; i++){
			if (locationPlayer.getItems().get(i).getDisplayChar() == 'v'){
				int dmg = locationPlayer.getItems().get(i).asWeapon().damage();
				this.hurt(dmg);
				if(!this.isConscious()){
					map.removeActor(this);
					display.println(this + " is killed.");


				}else {
					display.println(this + " " + locationPlayer.getItems().get(i).asWeapon().verb() + " with " + dmg + " damages!");
				}
			}
		}

		decayPowerStarEffect(display);
		// Handle multi-turn Actions
		ResetAction reset = ResetAction.getInstance();

		if (reset != null){
			actions.add(reset);}
		if (lastAction.getNextAction() != null)
			return lastAction.getNextAction();

		if (lastAction instanceof AttackAction) {
			AttackAction lastAttack = (AttackAction) lastAction;
			yoshi.attackEnemy(lastAttack.getEnemy());
		} else {
			yoshi.disengage();
		}

		// check at every turn if certain Status should be removed.
		if(!(this.hasCapability(Status.POWERSTAR))) {
			actions.add(addJump(map)); // if the player don't have the POWERSTAR Status, then add jump action
		}
		String ground;
		Location here = map.locationOf(this);

		if (here.getGround().getDisplayChar() == '#'){
			ground = "Wall";
		}
		else if (here.getGround().getDisplayChar() == '+'){
			ground = "Sprout";
		}
		else if (here.getGround().getDisplayChar() == 't'){
			ground = "Sapling";
		}
		else if (here.getGround().getDisplayChar() == 'T'){
			ground = "Mature";
		}
		else if (here.getGround().getDisplayChar() == '_'){
			ground = "Floor";
		}
		else{
			ground = "Dirt";
		}



		// return/print the console menu
		display.println("Mario" + this.printHp() + " at " + ground + "(" + map.locationOf(this).x() + ", " + map.locationOf(this).y() + ")");
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
	 * Add the SuperMushroom effect to the player when consumed
	 */
	public void addSuperMushroomEffect() {
		this.addCapability(Status.SUPERMUSHROOM);
		this.increaseMaxHp(50);
		this.setDisplayChar('M');
	}

	/**
	 * Remove the SuperMushroom effect from the player
	 */
	private void removeSuperMushroomEffect() {
		this.removeCapability(Status.SUPERMUSHROOM);
		this.setDisplayChar('m');
	}

	@Override
	public void hurt(int points) {
		if (getHp() <= 40 && yoshi.isConscious()) {
			yoshi.hurt(points);
		} else {
			super.hurt(points);
			removeSuperMushroomEffect();
		}
	}

	private int getHp() {
		String hpString = printHp().split("/")[0];
		hpString = hpString.substring(1);
		return Integer.valueOf(hpString);
	}


	/**
	 * Add the PowerStar effect to the player when consumed
	 */
	public void addPowerStarEffect() {
		this.addCapability(Status.POWERSTAR);
		powerStarEffectTicker = 10;
		this.heal(200);
	}

	/**
	 * Check how many turns that the player has the PowerStar effect (Max 10 turns)
	 * After 10 turns it will remove the effect
	 *
	 * @param display to display the text in the console
	 */
	private void decayPowerStarEffect(Display display) {
		if (powerStarEffectTicker <= 0) {
			this.removeCapability(Status.POWERSTAR);
		} else {
			display.println("Mario is INVINCIBLE!");
			powerStarEffectTicker -= 1;
		}
	}


	/**
	 * Check the surrounding Ground around the Player if it is a high ground
	 * Add the jump action to the actions List if the player doesn't have the POWERSTAR effect
	 *
	 * @param map the Game Map containing the Player
	 * @return List of jump action
	 */
	public List<Action> addJump(GameMap map){
		ArrayList<Action> actions = new ArrayList<Action>();

		int x = map.locationOf(this).x();
		int y = map.locationOf(this).y();

		List<Character> highGrounds = new ArrayList<Character>();
		highGrounds.add('#');
		highGrounds.add('+');
		highGrounds.add('t');
		highGrounds.add('T');

		for (int i = -1; i < 2; i++){
			for (int j = -1; j < 2; j++){
				if (!(x + i == x && y + j == y)){
					if (highGrounds.contains(map.at((x + i), (y + j)).getDisplayChar()) && x+i >= 0 && y+j >= 0){
						if (this.hasCapability(Status.POWERSTAR)){
							break;
						}
						else {
							Location highGround = map.at((x + i), (y + j));
							actions.add(new JumpBehaviour(highGround.getGround(), highGround));
						}
					}
				}
			}
		}
		return actions;
	}

	public Yoshi getYoshi() {
		return yoshi;
	}


}