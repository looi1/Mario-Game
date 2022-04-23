package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

public class ResetAction extends Action {
    private static int marker=0;
    private static ResetAction instance;

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

    @Override
    public String execute(Actor actor, GameMap map) {
            String result = "";



            ResetManager.getInstance().run(actor,map);

            result += "the game had been reset ";
            marker+=1;
            return result;

    }

    @Override
    public String menuDescription(Actor actor) {
        return "reset the game";
    }

    @Override
    public String hotkey() {
        return "r";
    }
}
