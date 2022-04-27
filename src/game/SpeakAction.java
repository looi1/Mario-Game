package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import java.util.*;

public class SpeakAction extends Action {

    private boolean toadSpeaking;
    private String[] voicelines = 
        {
            "You might need a wrench to smash Koopa's hard shells.",
            "You better get back to finding the Power Stars.",
            "The Princess is depending on you! You are our only hope.",
            "Being imprisoned in these walls can drive a fungus crazy :(",
        };

    public SpeakAction(boolean toadSpeaking) {
        this.toadSpeaking = toadSpeaking;
    }

    public String execute(Actor actor, GameMap map) {

        if (toadSpeaking) {
            for (Item item : actor.getInventory()) {
                if (item instanceof Wrench) {
                    voicelines[0] = null;
                }
            }

            if (actor.capabilitiesList().contains(Status.POWERSTAR)) {
                voicelines[1] = null;
            }

            Integer randomNumber;
            do {
            randomNumber = new Random().nextInt(voicelines.length);
            } while (voicelines[randomNumber].equals(null));
            return "Toad says: " + voicelines[randomNumber];
        } else {
            return "Mario is speaking to Toad";
        }
            
    }

    @Override
    public Action getNextAction() {
        if (!toadSpeaking) {
            return new SpeakAction(true);
        } else {
            return null;
        }
    }

    public String menuDescription(Actor actor) {
        return "Speak with Toad";
    }
}
