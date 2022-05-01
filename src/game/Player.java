package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.displays.Menu;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.ResetAction;
import game.reset.Resettable;
import game.items.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Class representing the Player.
 */
public class Player extends Actor implements Resettable {

	private final Menu menu = new Menu();
	private int marker = 0;
	private int powerStarEffectTicker;

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

	@Override
	public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {

		decayPowerStarEffect(display);
		actions.add(addJump(map));
		// Handle multi-turn Actions
		ResetAction reset = ResetAction.getInstance();

		if (reset != null){
			actions.add(reset);}
		if (lastAction.getNextAction() != null)
			return lastAction.getNextAction();


		// check at every turn if certain Status should be removed.
		if(this.hasCapability(Status.POWERSTAR)){
			Location locate = map.locationOf(this);

			if(map.at(locate.x(),locate.y()+1).getDisplayChar()=='#' || map.at(locate.x(),locate.y()+1).getDisplayChar()=='T' ){
				Coin coin = new Coin(5);

				map.at(locate.x()-1,locate.y()+1).addItem(coin);
			}
		}

		// return/print the console menu
		display.println(this.printHp() + "(" + map.locationOf(this).x() + ", " + map.locationOf(this).y() + ")");
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


	public void addSuperMushroomEffect() {
		this.addCapability(Status.SUPERMUSHROOM);

		this.increaseMaxHp(50);
		this.setDisplayChar('M');
	}

	private void removeSuperMushroomEffect() {
		this.removeCapability(Status.SUPERMUSHROOM);
		this.setDisplayChar('m');
	}

	@Override
	public void hurt(int points) {
		super.hurt(points);
		removeSuperMushroomEffect();
	}


	public void addPowerStarEffect() {
		this.addCapability(Status.POWERSTAR);
		powerStarEffectTicker = 10;
		this.increaseMaxHp(200);




	}

	private void decayPowerStarEffect(Display display) {
		if (powerStarEffectTicker <= 0) {
			this.removeCapability(Status.POWERSTAR);
		} else {
			display.println("Mario is INVINCIBLE!");
			powerStarEffectTicker -= 1;
		}
	}


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
					//System.out.println(highGrounds.contains(map.at((x + i), (y + j)).getDisplayChar()));
					if (highGrounds.contains(map.at((x + i), (y + j)).getDisplayChar()) && x+i >= 0 && y+j >= 01){
						Location highGround = map.at((x + i), (y + j));
						actions.add(new JumpBehaviour(highGround.getGround(), highGround));
					}
				}
			}
		}
		return actions;
	}


}