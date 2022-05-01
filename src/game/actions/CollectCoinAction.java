package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.items.Coin;
import game.Wallet;


public class CollectCoinAction extends Action {

    private final Coin coin;

    public CollectCoinAction(Coin coin) {
        this.coin = coin;
    }
    
    /**
	 * Add the item to the player's Wallet.
	 *
	 * @see Action#execute(Actor, GameMap)
	 * @param actor The actor performing the action.
	 * @param map The map the actor is on.
	 * @return a suitable description to display in the UI
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		map.locationOf(actor).removeItem(coin);
		Wallet.addCoin(coin);
		return actor + " picked up " + coin;
	}

    /**
	 * Description of this action for the menu.
	 *
	 * @see Action#menuDescription(Actor)
	 * @param actor The actor performing the action.
	 * @return a string, e.g. "Player picks up coin(20)"
	 */
	@Override
	public String menuDescription(Actor actor) {
		return actor + " picks up " + coin;
	}


    

}
