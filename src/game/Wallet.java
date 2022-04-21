package game;

public class Wallet {
    public static int totalBalance = 0;

    public static void addCoin(Coin coin) {
        totalBalance += coin.getValue();
    }

}
