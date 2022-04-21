package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

public class AttackBehaviour implements Behaviour{
    private final Actor player;

    // TODO: develop and use it to attack the player automatically.

    public AttackBehaviour(Actor newPlayer){
        this.player = newPlayer;

    }
    @Override
    public Action getAction(Actor actor, GameMap map) {
       return null;
    }


}
