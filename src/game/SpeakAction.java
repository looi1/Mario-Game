package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import java.util.*;

/**
 * Action for speaking with Toad
 */
public class SpeakAction extends Action {

    /**
     * Change state of action. False: Toad has not yet spoken. True: Toad says his lines.
     */
    private boolean toadSpeaking;

    /**
     * Array of all possible voicelines.
     */
    private String[] voicelines = 
        {
            "You might need a wrench to smash Koopa's hard shells.",
            "You better get back to finding the Power Stars.",
            "The Princess is depending on you! You are our only hope.",
            "Being imprisoned in these walls can drive a fungus crazy :(",
        };

    /**
     * Constructor.
     * @param toadSpeaking whether Toad is the one speaking when this action is executed.
     */
    public SpeakAction(boolean toadSpeaking) {
        this.toadSpeaking = toadSpeaking;
    }

    /**
	 * Add the item to the player's Wallet.
	 *
	 * @see Action#execute(Actor, GameMap)
	 * @param actor The actor performing the action.
	 * @param map The map the actor is on.
	 * @return a message that the Player is speaking to Toad, or the voiceline itself.
	 */
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

    /**
     * The next action: Toad's turn to speak.
     */
    @Override
    public Action getNextAction() {
        if (!toadSpeaking) {
            return new SpeakAction(true);
        } else {
            return null;
        }
    }

    /**
	 * Description of this action for the menu.
	 *
	 * @see Action#menuDescription(Actor)
	 * @param actor The actor performing the action.
	 * @return a description to be printed in the menu
	 */
    public String menuDescription(Actor actor) {
        return "Speak with Toad";
    }
}
