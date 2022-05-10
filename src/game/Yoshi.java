package game;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.*;
import game.actions.AttackAction;
import game.actions.FeedAction;
import game.behaviours.AttackBehaviour;
import game.behaviours.Behaviour;
import game.behaviours.FollowBehaviour;
import game.enemies.Enemies;
import game.items.Steak;
import game.actions.FeedAction;

import java.util.HashMap;
import java.util.Map;

public class Yoshi extends Actor{

    /**
     * target of Yoshi to follow/attack
     */
    private int timer;

    /**
	 * hash map that consists behaviour of Goomba
	 */
	private final Map<Integer, Behaviour> behaviours = new HashMap<>(); // priority, behaviour

    public Yoshi(Actor player) {
        super("Yoshi", 'y', 40);
        this.behaviours.put(10, new FollowBehaviour(player));
    }

    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
		ActionList actions = new ActionList();
		if(otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
            for (Object object : otherActor.getInventory()) {
                if (object instanceof Steak) {
                    actions.add(new FeedAction(this));
                    break;
                }
            }
		}
		return actions;
	}

	/**
     * Figure out what to do next.
     *
     * @see Actor#playTurn(ActionList, Action, GameMap, Display)
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        
        getHungryAgain();
        for (Behaviour Behaviour : behaviours.values()) {
            Action action = Behaviour.getAction(this, map);
            if (action != null)
                return action;
        }
        return new DoNothingAction();
    }

    /**
	 * method to implement the weapon Yoshi used to attack enemies
	 * @return
	 */
	@Override
	public IntrinsicWeapon getIntrinsicWeapon() {
        if (this.hasCapability(Status.FED)) {
            return new IntrinsicWeapon(20, "chomps");
        } else {
            return new IntrinsicWeapon(5, "bites");
        }
	}

    public void attackEnemy(Enemies enemy) {
        disengage();
        this.behaviours.put(9, new FollowBehaviour(enemy));
        this.behaviours.put(8, new AttackBehaviour(enemy));
    }

    public void disengage() {
        this.behaviours.remove(9);
        this.behaviours.remove(8);
    }

    public void eatSteak() {
        addCapability(Status.FED);
        addCapability(Status.ENTER_HIGH_GROUND);
        timer = 15;
        setDisplayChar('Y');
        increaseMaxHp(80);
    }

    public void getHungryAgain() {
        if (timer <= 0 && hasCapability(Status.FED)) {
            setDisplayChar('y');
            increaseMaxHp(40);
            removeCapability(Status.FED);
            removeCapability(Status.ENTER_HIGH_GROUND);
        } else {
            timer -= 1;
        }
    }

    @Override
    public void hurt(int points) {
        super.hurt(points);
        System.out.println("Yoshi helps Mario take " + points + " damage!");
        System.out.println(printHp());
    }

    

}
