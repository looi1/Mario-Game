package game;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.enemies.PiranhaPlant;
import game.ground.Jumpable;

import java.util.Arrays;
import java.util.List;

public class WarpPipe extends Ground implements Jumpable {
    private int turns = 0;
    private PiranhaPlant piranhaPlant;
    private GameMap gameMap1;
    private GameMap gameMap2;
    private Display display;
    private Location lastLocation;
    List<String> secondMap = Arrays.asList(
            "........................##........................",
            "...................L......#.......................",
            "..........................#............L..........",
            ".......L...................##..............L......",
            ".............................#....................",
            "..............................#.........L.........",
            "..............L.................#.................",
            "........................L......##.....L...........",
            "..............................##..................",
            "....L.L.L..............#____####..................",
            "...L..L..L............#_____###....L..............",
            "...L.L.L.L............#______###..................",
            "....L.L.L..............#_____###..................",
            "...............................##...........L.....",
            ".................................#................",
            "..................................#...............",
            "..........L........................#..............",
            ".........................L..........#......L......",
            ".....................................##...........");

    /**
     * Constructor.
     */
    public WarpPipe(GameMap firstMap, GameMap secondMap, Display display) {
        super('C');
        this.gameMap1 = firstMap;
        this.gameMap2 = secondMap;
        this.display = display;
    }

    public WarpPipe() {
        super('C');
    }

    @Override
    public void tick(Location location) {
        super.tick(location);

        this.turns += 1;

        if (this.turns == 1){
            this.piranhaPlant = new PiranhaPlant();
            location.addActor(this.piranhaPlant);
        }

        if (location.getActor() instanceof Player){
            this.gameMap1 = location.map();
            this.lastLocation = location;
            this.gameMap1.removeActor(location.getActor());
            Location teleportToLava = new Location(gameMap2, 0, 0);
            this.gameMap2.addActor(location.getActor(), teleportToLava);
            //this.gameMap2.draw(display);
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
