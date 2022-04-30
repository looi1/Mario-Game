package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.reset.ResetManager;

/**
 * Class to reset all resettable object
 */
public class ResetAction extends Action {
    /**
     * tracker to keep track how many times Reset been called
     */
    private static int marker=0;

    /**
     * instance of ResetAction
     */
    private static ResetAction instance;

    /**
     * Static factory method
     * @return instance of ResetAction
     */
    public static ResetAction getInstance(){
        if(marker==0){
            instance = new ResetAction();

        }else if(instance != null){
            instance = null;
        }
        return instance;
    }
    private ResetAction() {


    }

    /**
     * method to reset the game
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return
     */
    @Override
    public String execute(Actor actor, GameMap map) {
            String result = "";

            result+= actor.getInventory().toString()+" ";

            ResetManager.getInstance().run(actor,map);

            result += "the game had been reset ";
            marker+=1;
            return result;

    }

    /**
     * method to upgrade the console
     * @param actor The actor performing the action.
     * @return
     */
    @Override
    public String menuDescription(Actor actor) {
        return "reset the game";
    }

    /**
     * method to change the hotkey of reset action
     * @return
     */
    @Override
    public String hotkey() {
        return "r";
    }
}
