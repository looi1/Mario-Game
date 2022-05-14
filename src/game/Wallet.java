package game;

import game.items.Coin;
/**
 * Records and manages the coins collected.
 */
public class Wallet {
    /**
     * Total value pf collected Coins.
     */
    public static int totalBalance = 0;

    /**
     * For adding coins to the total balance.
     * @param coin the Coin colleccted
     */
    public static void addCoin(Coin coin) {
        totalBalance += coin.getValue();
    }

}
