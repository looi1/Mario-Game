package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.Player;
import game.Status;
import game.behaviours.Behaviour;
import game.enemies.Enemies;
import game.items.Fountains;
import game.items.HealthFountain;

public class DrinkAction extends Action {
    private Player player;
    private int playerDmg;
    private Fountains fountain;
    private boolean powerFoundCap;
    private boolean healthFoundCap;

    public DrinkAction(Fountains fountain){
        this.fountain = fountain;
        this.playerDmg = 5;
        this.powerFoundCap = true;
        this.healthFoundCap = true;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        String result = "";

        Location locate = map.locationOf(actor);

        if(locate.getItems().contains(fountain)){
            for(int i=0 ; i<locate.getItems().size();i++) {
                if (locate.getItems().get(i).hasCapability(Status.HEAL) && ((Fountains) locate.getItems().get(i)).useFound()) {
                    actor.heal(50);
                    ((Fountains) locate.getItems().get(i)).drink();
                    result = actor + " drank Health Fountain!";

                } else if(locate.getItems().get(i).hasCapability(Status.HEAL) && !(((Fountains) locate.getItems().get(i)).useFound() )){
                    this.healthFoundCap=false;
                    result += "Health Fountain is empty";
                }
                if (locate.getItems().get(i).hasCapability(Status.INDMG) && ((Fountains) locate.getItems().get(i)).useFound()) {

                    ((Player)actor).setDamage(15);
                    ((Fountains) locate.getItems().get(i)).drink();
                    result = actor + " drank Power Fountain!";
                }else if(locate.getItems().get(i).hasCapability(Status.INDMG) && !(((Fountains) locate.getItems().get(i)).useFound() )){
                    this.powerFoundCap=false;
                    result += "Power Fountain is empty";
                }

            }
        }

        return result;
    }

    @Override
    public String menuDescription(Actor actor) {

        return "drink fountain";}




    }


