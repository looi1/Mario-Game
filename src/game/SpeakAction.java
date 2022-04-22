package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import java.util.*;

public class SpeakAction extends Action {

    private String[] voicelines = 
        {
            "You might need a wrench to smash Koopa's hard shells.",
            "You better get back to finding the Power Stars.",
            "The Princess is depending on you! You are our only hope.",
            "Being imprisoned in these walls can drive a fungus crazy :(",
        };

    public SpeakAction() {

    }

    public String execute(Actor actor, GameMap map) {

        for (Item item : actor.getInventory()) {
            if (item instanceof Wrench) {
                voicelines[0] = null;
                System.out.println("has wrench");
            }
        }

        if (actor.hasCapability(Status.POWERSTAR)) {
            voicelines[1] = null;
            System.out.println("has powerstar");
        }

        Integer randomNumber;
        do {
        randomNumber = new Random().nextInt(voicelines.length);
        } while (voicelines[randomNumber].equals(null));
        return voicelines[randomNumber];
    }

    public String menuDescription(Actor actor) {
        return "Speak with Toad";
    }
}
