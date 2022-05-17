package game.actions;

import java.util.Random;

import edu.monash.fit2099.demo.conwayslife.Player;
import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.Weapon;
import game.behaviours.FollowBehaviour;
import game.behaviours.FrozenBehaviour;
import game.enemies.Shell;
import game.Status;
import game.enemies.Enemies;
import java.util.ArrayList;
import edu.monash.fit2099.engine.positions.Exit;
import game.behaviours.FrozenBehaviour;
import java.util.List;

/**
 * Special Action for attacking other Actors.
 */
public class AttackAction extends Action {

	/**
	 * The Enemies that is to be attacked
	 */
	protected Enemies target;

	/**
	 * The direction of incoming attack.
	 */
	protected String direction;

	/**
	 * Random number generator
	 */
	protected Random rand = new Random();

	/**
	 * Attack damage
	 */
	private int damage;

	/**
	 * random number
	 */

	private int randnum;

	/**
	 * Constructor.
	 * 
	 * @param target the Actor to attack
	 */
	public AttackAction(Enemies target, String direction) {
		this.target = target;
		this.direction = direction;
	}

	@Override
	public String execute(Actor actor, GameMap map) {

		Weapon weapon = actor.getWeapon();

		if(actor.hasCapability(Status.POWERSTAR)){
			this.damage = target.getHp();
			this.randnum = 0; //since both player and enemies have hitpoint rate of 50, so i set it as a constant here if player consume power star to ensure every hits

		}else{
			this.damage = weapon.damage();
			this.randnum = rand.nextInt(100);
		}

		if (!( this.randnum <= weapon.chanceToHit())) {
			return actor + " misses " + target + ".";
		}

		if (actor.hasCapability(Status.FREEZE)) {
			if (rand.nextInt(100) >= 40) {
				target.addBehaviour(1, new FrozenBehaviour());
			}
		}


		String result = actor + " " + weapon.verb() + " " + target + " for " + damage + " damage.";
		target.hurt(damage);
		target.addBehaviour(9,new FollowBehaviour(actor));

		if (!target.isConscious()) {
			ActionList dropActions = new ActionList();
			// drop all items
			for (Item item : target.getInventory())
				dropActions.add(item.getDropAction(actor));
			for (Action drop : dropActions)
				drop.execute(target, map);
			// remove actor

			Location locate = map.locationOf(target);
			map.removeActor(target);

			if(target.hasCapability(Status.HAS_SHELL)){
				Shell shell = new Shell();
				map.addActor(shell,locate);
			}
			result += System.lineSeparator() + target + " is killed.";
		}

		return result;
	}

	

	@Override
	public String menuDescription(Actor actor) {
		return actor + " attacks " + target + " at " + direction;
	}

	public Enemies getEnemy() {
		return target;
	}
}
