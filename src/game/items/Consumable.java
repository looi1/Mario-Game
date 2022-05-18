package game.items;

/**
 * interface for all Consumable items
 */
public interface Consumable {

    /**
     * Get the Status associated with the item
     * @return Status
     */
    public Enum<?> getConsumeStatus();
}
