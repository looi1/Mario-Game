package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import java.util.*;

public class TradeAction extends Action {

    private ArrayList<SellableItems> shop = new ArrayList<SellableItems>();
    
    public TradeAction() {
        
	}


    @Override
	public String execute(Actor actor, GameMap map) {
        
		

		return "";
	}

    public String menuDescription(Actor actor) {
        return "";
    }

    public ArrayList<SellableItems> getSellableItems() {
        return new ArrayList<SellableItems>(this.shop);
    }

}
