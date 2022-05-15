package game;

import edu.monash.fit2099.engine.positions.Ground;
import game.ground.Jumpable;

public class WarpPipe extends Ground implements Jumpable {
    /**
     * Constructor.
     */
    public WarpPipe() {
        super('C');
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
