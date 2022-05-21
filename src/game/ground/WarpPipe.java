package game.ground;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.positions.World;
import game.Player;
import game.enemies.PiranhaPlant;
import game.ground.Jumpable;

import java.util.Arrays;
import java.util.List;

/**
 * A WarpPie class that extends Ground and implements Jumpable to teleport the Player from one map to the other
 *
 * @see edu.monash.fit2099.engine.positions.Ground
 * @see game.ground.Jumpable
 */
public class WarpPipe extends Ground implements Jumpable {

    /**
     * Turns integer to keep track of game turn
     */
    private int turns = 0;

    /**
     * PiranhaPlant object on top of the Warp Pipe
     */
    private PiranhaPlant piranhaPlant;

    /**
     * First map -> Normal Map
     */
    private GameMap gameMap1;

    /**
     * Second map -> Lava zone
     */
    private GameMap gameMap2;

    /**
     * The world that the Player is in
     */
    private World world;

    /**
     * To store the last location of the Player before teleporting to Lava zone
     */
    private static Location lastLocation;

    /**
     * Constructor without parameter
     */
    public WarpPipe() {
        super('C');
    }

    /**
     * Constructor with parameter
     *
     * @param firstMap the first Map
     * @param secondMap the second Map
     * @param world the World that the Player is in
     */
    public WarpPipe(GameMap firstMap, GameMap secondMap, World world) {
        super('C');
        this.gameMap1 = firstMap;
        this.gameMap2 = secondMap;
        this.world = world;
    }

    /**
     * Ground can also experience the joy of time.
     *
     * @param location The location of the Ground
     */
    @Override
    public void tick(Location location) {
        super.tick(location);

        this.turns += 1;

        if (this.turns == 1 && location.x() != 0 && location.y() != 0){
            this.piranhaPlant = new PiranhaPlant();
            location.addActor(this.piranhaPlant);
        }

        if (location.getActor() instanceof Player && gameMap1.contains(location.getActor())){
            lastLocation = location;
            Actor mario = location.getActor();
            this.gameMap1.removeActor(location.getActor());
            Location newLocation = new Location(gameMap2, 0, 1);
            this.world.addPlayer(mario, newLocation);
        } else if (location.getActor() instanceof Player && !gameMap1.contains(location.getActor())){
            Actor mario = location.getActor();
            this.gameMap2.removeActor(mario);
            this.world.addPlayer(mario, lastLocation);
        }
    }

    /**
     * A method to check if the Piranha Plant on top of the Warp Pipe have been killed or not
     *
     * @return boolean of the piranhaPlant.isConscious()
     */
    public boolean piranhaPlantAlive(){
        if (this.piranhaPlant != null) {
            return this.piranhaPlant.isConscious();
        }
        return false;
    }

    /**
     * Override this to implement impassable terrain, or terrain that is only passable if conditions are met.
     *
     * @param actor the Actor to check
     * @return true
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        if (this.piranhaPlant != null){
            return piranhaPlantAlive();
        }
        return false;
    }

    /**
     * A getter method to get the high ground jump success rate
     *
     * @return jump success rate of the high ground
     */
    @Override
    public int getSuccessRate() {
        return 100;
    }

    /**
     * A getter method to get the fall damage from the high ground if failed to jump
     *
     * @return fall damage from high ground
     */
    @Override
    public int getFallDamage() {
        return 0;
    }

    /**
     * A getter method to get the name of the high ground
     *
     * @return high ground name
     */
    @Override
    public String getName() {
        return "Warp Pipe";
    }
}
