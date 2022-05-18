package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;

import edu.monash.fit2099.engine.actors.Actor;

import edu.monash.fit2099.engine.positions.GameMap;

/**
 * Behaviour of being Frozen
 */
public class FrozenBehaviour extends Action implements Behaviour {

    /**
     * Constructor
     */
    public FrozenBehaviour() {}

    /**
     * Actor that is Frozen does nothing. Only prints that it is Frozen
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        return actor + " is frozen!";
    }

    /**
     * Return this behaviour as action
     */
    @Override
	public Action getAction(Actor actor, GameMap map) {
		return this;
	}

    /**
     * Description
     */
    @Override
    public String menuDescription(Actor actor) {
        return "";
    }
}
