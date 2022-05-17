package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;

import edu.monash.fit2099.engine.actors.Actor;

import edu.monash.fit2099.engine.positions.GameMap;


public class FrozenBehaviour extends Action implements Behaviour {

    public FrozenBehaviour() {
        
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        return actor + " is frozen!";
    }

    @Override
	public Action getAction(Actor actor, GameMap map) {
		return this;
	}

    @Override
    public String menuDescription(Actor actor) {
        return "";
    }
}
