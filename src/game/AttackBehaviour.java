package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.MoveActorAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.Weapon;

import java.util.Random;

public class AttackBehaviour extends Action implements Behaviour{
    private final Actor player;
    protected Random rand = new Random();

    // TODO: develop and use it to attack the player automatically.

    public AttackBehaviour(Actor newPlayer){
        this.player = newPlayer;

    }
    public String execute(Actor actor, GameMap map) {
        Weapon weapon = actor.getWeapon();

        if (!(rand.nextInt(100) <= weapon.chanceToHit())) {
            return actor + " misses " + this.player + ".";}

        int damage = weapon.damage();
        String result = actor + " " + weapon.verb() + " " + this.player + " for " + damage + " damage.";
        this.player.hurt(damage);


        if (!this.player.isConscious()) {

            map.removeActor(this.player);
            result += System.lineSeparator() + this.player + " is killed.";
        }


        return result;
    }



    @Override
    public Action getAction(Actor actor, GameMap map) {
        if (!map.contains(this.player) || !map.contains(actor))
            return null;

        //Location locaEnemies = map.locationOf(actor);
        //Location locaPlayer = map.locationOf(this.player);

        Location here = map.locationOf(actor);
        Location there = map.locationOf(this.player);

        if ((here.x() == there.x() || here.y() == there.y())&&distance(here,there)<=1) {
            return this;
        }else if ((here.x() != there.x() && here.y() != there.y())&&distance(here,there)<=2){ //diagonal
            return this;}
        else{
            return null;
        }

    }
    private int distance(Location a, Location b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }

    @Override
    public String menuDescription(Actor actor) {
        return "";
    }




}
