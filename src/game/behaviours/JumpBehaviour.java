package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.MoveActorAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.Status;
import game.ground.Jumpable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JumpBehaviour extends Action implements Behaviour {
    private Ground target;
    private Jumpable jumpableTarget;
    private Random r = new Random();
    private Location highGroundLocation;
    
    /**
     * Constructor.
     *
     *   to check the target ground that the player want to jump against
     */
    public JumpBehaviour(Ground highGround, Location highGroundLoc) {
        this.target = highGround;
        this.highGroundLocation = highGroundLoc;
        this.jumpableTarget = (Jumpable) highGround;
    }

    @Override
    public Action getAction(Actor actor, GameMap map) {

        Location playerLocation = map.locationOf(actor);

        if (target.canActorEnter(actor) || !map.contains(actor))
            return null;

        int currentDistance = distance(playerLocation, highGroundLocation);
        System.out.println(currentDistance);

        for (Exit exit : playerLocation.getExits()) {
            Location destination = exit.getDestination();
            System.out.print("x = " + destination.x());
            System.out.println(" y = " + destination.y());
            System.out.println(!(destination.canActorEnter(actor)));
            if (!(destination.canActorEnter(actor))) {
                int newDistance = distance(destination, highGroundLocation);
                System.out.println(newDistance);

                if (newDistance < currentDistance) {
                    System.out.println(exit.getName());
                    map.moveActor(actor, destination);
                    return new MoveActorAction(destination, exit.getName());
                }
            }
        }
        return null;
    }


    @Override
    public String execute(Actor actor, GameMap map) {
        int successRate;
        if (actor.hasCapability(Status.SUPERMUSHROOM)){
            successRate = 100;
        } else {
            successRate = jumpableTarget.getSuccessRate();
        }

        if (r.nextInt(101) <= successRate){
            getAction(actor, map);
            return "Mario successfully jumped to a " + jumpableTarget.getName();
        } else {
            actor.hurt(jumpableTarget.getFallDamage());
            return "ARGHHH!! Mario failed to jump. Mario receives " + jumpableTarget.getFallDamage() + " damage.";
        }
    }

    @Override
    public String menuDescription(Actor actor) {
        return "Jump to a " + jumpableTarget.getName() + "(" + this.highGroundLocation.x() + ", " + this.highGroundLocation.y() + ")";
    }


    private int distance(Location a, Location b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }
}
