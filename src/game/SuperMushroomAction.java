package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

public class SuperMushroomAction extends Action {

    public static SuperMushroom getInstance(){

        for(int i; i<)
        if(marker==0){
            instance = new ResetAction();

        }else if(instance != null){
            instance = null;
        }
        return instance;
    }
    private SuperMushroom() {


    }

    @Override
    public String execute(Actor actor, GameMap map) {
        String result = "";


        return result;

    }

    @Override
    public String menuDescription(Actor actor) {
        return "reset the game";
    }



}
