package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.enemies.Shell;


/**
 * a class to break the shell
 */
public class BreakShellAction extends Action {

    /**
     * Shell that is to break
     */
    private Actor targetShell;

    /**
     * location of the shell
     */
    private Location locationShell;


    /**
     * constructor
     * @param newTargetShell the shell that is to break
     */
    public BreakShellAction(Actor newTargetShell) {
        this.targetShell = newTargetShell;

    }

    @Override
    public String execute(Actor actor, GameMap map) {
        locationShell = map.locationOf(targetShell);
        map.removeActor(targetShell);

        return ((Shell)targetShell).broken(actor, locationShell);
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " break the "+targetShell;
    }
}
