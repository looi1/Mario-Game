package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.Weapon;
import game.Player;
import game.Status;
import game.enemies.Enemies;
import game.enemies.Fire;
import game.items.Fountains;

import java.util.Random;

public class DrinkBehaviour extends Action implements Behaviour {

    /**
     * actor to be attacked
     */
    private Fountains fountain;

    /**
     * generate raondom number
     */
    protected Random rand = new Random();

    /**
     * attack damage
     */
    private int damage;

    // TODO: develop and use it to attack the player automatically.

    /**
     * constructor
     */
    public DrinkBehaviour() {


    }

    public String execute(Actor actor, GameMap map) {
        String result = "";

        Location locate = map.locationOf(actor);


        for (int i = 0; i < locate.getItems().size(); i++) {
            if (locate.getItems().get(i).hasCapability(Status.HEAL) && ((Fountains) locate.getItems().get(i)).useFound()) {
                actor.heal(50);
                ((Fountains) locate.getItems().get(i)).drink();
                result+= actor+ " Drank Health Fountain";


            } else if (locate.getItems().get(i).hasCapability(Status.HEAL) && !(((Fountains) locate.getItems().get(i)).useFound())) {
                return "Health Fountain is empty !";

            }
            if (locate.getItems().get(i).hasCapability(Status.INDMG) && ((Fountains) locate.getItems().get(i)).useFound()) {

                ((Enemies) actor).setDamage(15);
                ((Fountains) locate.getItems().get(i)).drink();
                result+= actor+ " Drank Power Fountain";



            } else if (locate.getItems().get(i).hasCapability(Status.INDMG) && !(((Fountains) locate.getItems().get(i)).useFound())) {
                return "Power Fountain is empty !";

            }

        }


        return result;


    }

    @Override
    public String menuDescription(Actor actor) {
        return "Drink Fountain";
    }

    /**
     * return the attack action if player is within the enemy surroundings
     *
     * @param actor the Actor acting
     * @param map   the GameMap containing the Actor
     * @return
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        Location actorLoc = map.locationOf(actor);

        for (int i = 0; i < actorLoc.getItems().size(); i++) {

            if (actorLoc.getItems().get(i).hasCapability(Status.HEAL) || (actorLoc.getItems().get(i).hasCapability(Status.INDMG))) {
                return this;
            } }
                return null;




    }
}
