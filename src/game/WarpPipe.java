package game;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.behaviours.JumpBehaviour;
import game.enemies.PiranhaPlant;
import game.ground.Jumpable;

public class WarpPipe extends Ground implements Jumpable {
    private int turns = 0;
    private PiranhaPlant piranhaPlant;

    /**
     * Constructor.
     */
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
