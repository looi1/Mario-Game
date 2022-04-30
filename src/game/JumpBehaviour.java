package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.MoveActorAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JumpBehaviour extends Action implements Behaviour {
    private Ground target;
    private Random r = new Random();
    private int successRate;
    private String name;
    Location highGroundLocation;
    
    /**
     * Constructor.
     *
     *   to check the target ground that the player want to jump against
     */
    public JumpBehaviour(Ground highGround, Location highGroundLoc) {
        this.target = highGround;
        this.highGroundLocation = highGroundLoc;
    }

    @Override
    public Action getAction(Actor actor, GameMap map) {
        ArrayList<Action> actions = new ArrayList<Action>();

        Location playerLocation = map.locationOf(actor);

        int x = playerLocation.x();
        int y = playerLocation.y();

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
        System.out.println("M = " + actor.hasCapability(Status.SUPERMUSHROOM));
        if (actor.hasCapability(Status.SUPERMUSHROOM)){
            this.successRate = 100;
        }
        else if (target.getDisplayChar() == '+') {
            this.successRate = 90;
        }
        else if (target.getDisplayChar() == 't'){
            this.successRate = 80;
        }
        else if (target.getDisplayChar() == 'T'){
            this.successRate = 70;
        }
        else {
            this.successRate = 80;
        }

        if (r.nextInt(101) <= this.successRate){
            getAction(actor, map);
            return "Mario successfully jumped to a " + this.name;
        }

        int fallDamage = 100 - this.successRate;
        actor.hurt(fallDamage);

        return "ARGHHH!! Mario failed to jump.";

    }

    @Override
    public String menuDescription(Actor actor) {

        if (target.getDisplayChar() == '+') {
            this.name = "Sprout";
        }
        else if (target.getDisplayChar() == 't'){
            this.name = "Sapling";
        }
        else if (target.getDisplayChar() == 'T'){
            this.name = "Mature";
        }
        else {
            this.name = "Wall";
        }

        return "Jump to a " + this.name + "(" + this.highGroundLocation.x() + ", " + this.highGroundLocation.y() + ")";
    }


    private int distance(Location a, Location b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }
}
