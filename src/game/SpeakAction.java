package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import java.util.*;

public class SpeakAction extends Action {

    private Map<Integer, String> voicelines = new HashMap<>();

    public SpeakAction() {
        voicelines.put(1, "You might need a wrench to smash Koopa's hard shells.");
        voicelines.put(2, "You better get back to finding the Power Stars.");
        voicelines.put(3, "The Princess is depending on you! You are our only hope.");
        voicelines.put(4, "Being imprisoned in these walls can drive a fungus crazy :(");
    }

    public String execute(Actor actor, GameMap map) {

        // if player.inventory.haswrench, remove first from voicelines
        for (Item item : actor.getInventory()) {
        }

        // if player.capability.hascapability, remove second from voicelines
        if (actor.hasCapability(Status.POWERSTAR)) {
            voicelines.remove(2);
        }

        // return a random voiceline from voicelines.
        return "";
    }

    public String menuDescription(Actor actor) {
        return "Speak with Toad";
    }
}
