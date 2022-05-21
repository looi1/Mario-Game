package game.ground;

/**
 * Interface for all higher ground
 */
public interface Jumpable {
    /**
     * A getter method to get the high ground jump success rate
     *
     * @return jump success rate of the high ground
     */
    public int getSuccessRate();

    /**
     * A getter method to get the fall damage from the high ground if failed to jump
     *
     * @return fall damage from high ground
     */
    public int getFallDamage();

    /**
     * A getter method to get the name of the high ground
     *
     * @return high ground name
     */
    public String getName();
}
