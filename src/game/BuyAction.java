package game;

import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import java.util.*;

public class BuyAction extends Action {
    private final SellableItem item;
    

    public BuyAction(SellableItem item) {
        this.item = item;
    }
    
    @Override
    public String execute(Actor actor, GameMap map) {
        if (item.getPrice() <= Wallet.totalBalance) {
            Wallet.totalBalance -= item.getPrice();
            actor.addItemToInventory((Item) item);
            return "Bought " + item;
        } else {
            return "You don't have enough coins!";
        }
    }

    @Override
    public String menuDescription(Actor actor) {
        return "Buy " + item + " ($" + item.getPrice() + ")" ;
    }

    
}
