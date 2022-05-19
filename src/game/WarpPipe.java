package game;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.positions.World;
import game.enemies.PiranhaPlant;
import game.ground.Jumpable;

import java.util.Arrays;
import java.util.List;

public class WarpPipe extends Ground implements Jumpable {
    private int turns = 0;
    private PiranhaPlant piranhaPlant;
    private GameMap gameMap1;
    private GameMap gameMap2;
    private World world;
    private Location lastLocation;

    /**
     * Constructor.
     */
    public WarpPipe(GameMap firstMap, GameMap secondMap, World world) {
        super('C');
        this.gameMap1 = firstMap;
        this.gameMap2 = secondMap;
        this.world = world;
    }

    public WarpPipe() {
        super('C');
    }

    @Override
    public void tick(Location location) {
        super.tick(location);

        this.turns += 1;

        if (this.turns == 1 && location.x() != 0 && location.y() != 0){
            this.piranhaPlant = new PiranhaPlant();
            location.addActor(this.piranhaPlant);
        }

        System.out.println();
        if (location.getActor() instanceof Player && gameMap1.contains(location.getActor())){
            this.gameMap1 = location.map();
            this.lastLocation = location;
            Actor mario = location.getActor();
            this.gameMap1.removeActor(location.getActor());
            Location teleportToLava = new Location(gameMap2, 0, 0);
            this.world.addPlayer(mario, teleportToLava);
        }
        else if (location.getActor() instanceof Player && gameMap2.contains(location.getActor()) && lastLocation != null){
            Actor mario = location.getActor();
            this.gameMap2.removeActor(location.getActor());
            Location teleportBack = new Location(gameMap1, this.lastLocation.x(), this.lastLocation.y());
            this.world.addPlayer(mario, teleportBack);
        }
    }

    public boolean piranhaPlantAlive(){
        if (this.piranhaPlant != null) {
            return this.piranhaPlant.isConscious();
        }
        return false;
    }

    @Override
    public boolean canActorEnter(Actor actor) {
        if (this.piranhaPlant != null){
            return piranhaPlantAlive();
        }
        return false;
    }

    @Override
    public int getSuccessRate() {
        return 100;
    }

    @Override
    public int getFallDamage() {
        return 0;
    }

    @Override
    public String getName() {
        return "Warp Pipe";
    }
}
