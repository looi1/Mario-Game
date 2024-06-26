package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.MoveActorAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.Weapon;
import game.Status;
import game.Player;
import game.enemies.Enemies;
import game.enemies.Fire;

import java.util.Random;

/**
 * class representing the attack behaviour of enemies and Yoshi
 */
public class AttackBehaviour extends Action implements Behaviour {
    /**
     * actor to be attacked
     */
    private final Actor player;

    /**
     * generate raondom number
     */
    protected Random rand = new Random();

    /**
     * attack damage
     */
    private int damage;

    /**
     * constructor
     * 
     * @param newPlayer
     */
    public AttackBehaviour(Actor newPlayer) {
        this.player = newPlayer;

    }

    public String execute(Actor actor, GameMap map) {

        Weapon weapon = actor.getWeapon();
        if (this.player.hasCapability(Status.POWERSTAR)) {
            this.damage = 0;
        } else {
            this.damage = weapon.damage();
        }

        if (!(rand.nextInt(100) <= weapon.chanceToHit())) {
            return actor + " misses " + this.player + ".";
        }

        String result = actor + " " + weapon.verb() + " " + this.player + " for " + damage + " damage.";
        this.player.hurt(damage);
        if(actor instanceof Enemies && !actor.hasCapability(Status.CANTMOVE)){
            ((Enemies)actor).addBehaviour(5,new FollowBehaviour(this.player));}


        if (actor.hasCapability(Status.FIREATK)){
            Location locate = map.locationOf(this.player);
            locate.addItem(new Fire());
            //map.addActor(new Fire(),locate);
        }

        /*Location locatePlayer = map.locationOf(this.player);
        for(int i = 0; i<locatePlayer.getItems().size() ; i++){
            if (locatePlayer.getItems().get(i).getDisplayChar() == 'v'){
                this.player.hurt(20);
            }
            }*/

        if (this.player instanceof Player) {
            Player mario = (Player) this.player;
            if (!mario.getYoshi().isConscious() && map.contains(mario.getYoshi())) {
                map.removeActor(mario.getYoshi());
                System.out.println("Yoshi sacrificed himself for you!");
            }
        }

        if (!this.player.isConscious()) {
            map.removeActor(this.player);
            result += System.lineSeparator() + this.player + " is killed.";
        }

        return result;
    }

    /**
     * return the attack action if player is within the enemy surroundings
     * 
     * @param actor the Actor acting
     * @param map   the GameMap containing the Actor
     * @return
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        if (!map.contains(this.player) || !map.contains(actor))
            return null;

        // Location locaEnemies = map.locationOf(actor);
        // Location locaPlayer = map.locationOf(this.player);

        Location here = map.locationOf(actor);
        Location there = map.locationOf(this.player);

        if ((here.x() == there.x() || here.y() == there.y()) && distance(here, there) <= 1) {
            return this;
        } else if ((here.x() != there.x() && here.y() != there.y()) && distance(here, there) <= 2) { // diagonal
            return this;
        } else {
            return null;
        }

    }

    /**
     * calculate the distance between 2 location
     * 
     * @param a location a
     * @param b location b
     * @return
     */
    private int distance(Location a, Location b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }

    @Override
    public String menuDescription(Actor actor) {
        return "";
    }

}
