package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.Player;
import game.Status;
import game.items.Bottle;
import game.items.Fountains;

/**
 * Class that figures out an Action to drink from the Bottle or from the Fountain.
 *
 * @see edu.monash.fit2099.engine.actions.Action
 */
public class DrinkAction extends Action {

    /**
     * Fountains object
     */
    private Fountains fountain;

    /**
     * Constructor
     *
     * @param fountain Fountains object
     */
    public DrinkAction(Fountains fountain) {
        this.fountain = fountain;

    }

    /**
     * Perform the Action.
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return a description of what happened that can be displayed to the user.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        String result = "";

        Location locate = map.locationOf(actor);

        if (locate.getItems().contains(fountain) && !(actor.hasCapability(Status.HAS_BOTTLE))) {
            for (int i = 0; i < locate.getItems().size(); i++) {
                if (locate.getItems().get(i).hasCapability(Status.HEAL) && ((Fountains) locate.getItems().get(i)).useFound()) {
                    actor.heal(50);
                    ((Fountains) locate.getItems().get(i)).drink();
                    result = actor + " drank Health Fountain!";

                } else if (locate.getItems().get(i).hasCapability(Status.HEAL) && !(((Fountains) locate.getItems().get(i)).useFound())) {
                    result += "Health Fountain is empty";
                }

                if (locate.getItems().get(i).hasCapability(Status.INDMG) && ((Fountains) locate.getItems().get(i)).useFound()) {
                    ((Player) actor).setDamage(15);
                    ((Fountains) locate.getItems().get(i)).drink();
                    result = actor + " drank Power Fountain!";
                } else if (locate.getItems().get(i).hasCapability(Status.INDMG) && !(((Fountains) locate.getItems().get(i)).useFound())) {
                    result += "Power Fountain is empty";
                }
            }
        }
        else if (actor.hasCapability(Status.HAS_BOTTLE) && locate.getItems().contains(fountain)) {
            for (int i = 0; i < locate.getItems().size(); i++) {
                if (locate.getItems().get(i).hasCapability(Status.HEAL) && ((Fountains) locate.getItems().get(i)).useFound()) {
                    for (int j = 0; j < actor.getInventory().size(); j++){
                        if (actor.getInventory().get(j) instanceof Bottle){
                            ((Fountains) locate.getItems().get(i)).drink();
                            ((Bottle) actor.getInventory().get(j)).push(fountain);
                        }
                    }
                    result = "Mario filled the Magical Bottle with Health water!";

                } else if (locate.getItems().get(i).hasCapability(Status.HEAL) && !(((Fountains) locate.getItems().get(i)).useFound())) {
                    result += "Health Fountain is empty";
                }

                if (locate.getItems().get(i).hasCapability(Status.INDMG) && ((Fountains) locate.getItems().get(i)).useFound()) {
                    for (int j = 0; j < actor.getInventory().size(); j++){
                        if (actor.getInventory().get(j) instanceof Bottle){
                            ((Fountains) locate.getItems().get(i)).drink();
                            ((Bottle) actor.getInventory().get(j)).push(fountain);
                        }
                    }
                    result = "Mario filled the Magical Bottle with Power water!";
                } else if (locate.getItems().get(i).hasCapability(Status.INDMG) && !(((Fountains) locate.getItems().get(i)).useFound())) {
                    result += "Power Fountain is empty";
                }
            }
        }
        return result;
    }

    /**
     * Returns a descriptive string
     *
     * @param actor The actor performing the action.
     * @return the text we put on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        if (actor.hasCapability(Status.HAS_BOTTLE)) {
            return "Mario refill " + this.fountain.getName();
        }
        return "Drink " + this.fountain.getName();
    }
}


