package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

public class BreakShellAction extends Action {
    private Actor targetShell;
    private Location locationShell;

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
