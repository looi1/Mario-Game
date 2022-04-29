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
    
    /**
     * Constructor.
     *
     * @param highGround to check the target ground that the player want to jump against
     */
    public JumpBehaviour(Ground highGround) {
        this.target = highGround;
        if (target.getDisplayChar() == '+') {
            this.successRate = 90;
        } else if (target.getDisplayChar() == 't') {
            this.successRate = 80;
        } else if (target.getDisplayChar() == 'T') {
            this.successRate = 70;
        } else {
            this.successRate = 80;
        }
    }

    @Override
    public Action getAction(Actor actor, GameMap map) {
        ArrayList<Action> actions = new ArrayList<Action>();

        if (target.canActorEnter(actor) || !map.contains(actor))
            return null;

        Location playerLocation = map.locationOf(actor);
        Location highGroundLocation = null;

        int x = playerLocation.x();
        int y = playerLocation.y();
        int high2 = 3;
        int low2 = -1;
        int randomX = r.nextInt((high2 - low2) + low2) - 1;
        int randomY = r.nextInt((high2 - low2) + low2) - 1;
        List<Character> highGround = new ArrayList<Character>();
        highGround.add('#');
        highGround.add('+');
        highGround.add('t');
        highGround.add('T');

        if (randomX != 0 && randomY != 0){
            if (highGround.contains(map.at((randomX + x), (randomY + y)).getGround().getDisplayChar())) {
                actions.add(new JumpBehaviour(map.at((randomX + x), (randomY + y)).getGround()));

                highGroundLocation = map.at((randomX + x), (randomY + y));
            }
        }

        int currentDistance = distance(playerLocation, highGroundLocation);


        for (Exit exit : playerLocation.getExits()) {
            Location destination = exit.getDestination();
            if (!(destination.canActorEnter(actor))) {
                int newDistance = distance(destination, highGroundLocation);

                if (newDistance < currentDistance) {
                    return new MoveActorAction(destination, exit.getName());
                }
            }
        }

        return null;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        if (actor.hasCapability(Status.SUPERMUSHROOM)){
            this.successRate = 100;
        }

        if ((r.nextInt((101)) <= this.successRate)){
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

        return "Jump to a " + this.name;
    }

    @Override
    public String hotkey() {
        return "j";
    }

    private int distance(Location a, Location b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }
}
