package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.MoveActorAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.Status;
import game.ground.Jumpable;

import java.util.Random;

/**
 * A class that figures out a MoveActorAction that will move the actor to the
 * high ground
 * 
 * @see edu.monash.fit2099.engine.actions.MoveActorAction
 * @see game.behaviours.Behaviour
 * @see edu.monash.fit2099.engine.actions.Action
 * 
 */
public class JumpBehaviour extends Action implements Behaviour {
    /**
     * target of the high ground
     */
    private Ground target;

    /**
     * instantiate a new Random class r
     */
    private Random r = new Random();

    /**
     * Location of the high ground
     */
    Location highGroundLocation;

    /**
     * target cast into type Jumpable.
     */
    private Jumpable jumpableTarget;

    /**
     * Constructor.
     *
     * @param highGround    the high ground that the actor want to jump onto
     * @param highGroundLoc the location of the high ground
     */
    public JumpBehaviour(Ground highGround, Location highGroundLoc) {
        this.target = highGround;
        this.highGroundLocation = highGroundLoc;
        this.jumpableTarget = (Jumpable) highGround;
    }

    /**
     * Get the action MoveActorAction when the player want to jump onto a high
     * ground
     *
     * @param actor the Actor acting
     * @param map   the GameMap containing the Actor
     * @return an Action that actor can perform, or null if actor can't do this.
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {

        Location playerLocation = map.locationOf(actor);

        if (target.canActorEnter(actor) || !map.contains(actor))
            return null;

        int currentDistance = distance(playerLocation, highGroundLocation);

        for (Exit exit : playerLocation.getExits()) {
            Location destination = exit.getDestination();
            if (!(destination.canActorEnter(actor))) {
                int newDistance = distance(destination, highGroundLocation);

                if (newDistance < currentDistance) {
                    map.moveActor(actor, destination);
                    return new MoveActorAction(destination, exit.getName());
                }
            }
        }
        return null;
    }

    /**
     * Perform the Action.
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return a description of what happened that can be displayed to the user.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        int successRate;
        if (actor.hasCapability(Status.SUPERMUSHROOM)) {
            successRate = 100;
        } else {
            successRate = jumpableTarget.getSuccessRate();
        }

        if (r.nextInt(101) <= successRate) {
            return "Mario successfully jumped to a " + jumpableTarget.getName();
        } else {
            actor.hurt(jumpableTarget.getFallDamage());
            return "ARGHHH!! Mario failed to jump. Mario receives " + jumpableTarget.getFallDamage() + " damage.";
        }

    }

    /**
     * Returns a descriptive string
     *
     * @param actor The actor performing the action.
     * @return the text we put on the menu
     */
    @Override
    public String menuDescription(Actor actor) {

        return "Jump to a " + jumpableTarget.getName() + "(" + this.highGroundLocation.x() + ", " + this.highGroundLocation.y() + ")";
    }

    /**
     * Compute the Manhattan distance between two locations.
     *
     * @param a the first location
     * @param b the first location
     * @return the number of steps between a and b if you only move in the four
     *         cardinal directions.
     */
    private int distance(Location a, Location b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }
}
