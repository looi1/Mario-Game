# mario-s1-2022

Assignment repo for Semester 1 - 2022

Creative mode

# Requirement 4

**Title**:
Yoshi, an adventure companion

**Description**:
Mario has Yoshi as a companion. Yoshi follows Mario, helps attack enemies, and if Mario is low on health, will sacrifice himself to take damage for Mario. Mario can also feed Yoshi to make him able to fly, have higher damage and higher health.

**Explanation why it adheres to SOLID principles** (WHY):
- SCP: Yoshi, FeedAction and Steak are their own separate classes, representing and handling the features of their own specific objects.
- OCP: Steak implements Sellable. As a new Sellable object Steak only needs to implement Sellable.getPrice() method without a need to check for the Sellable item's type to get its price.
- DIP: Yoshi has an association with the abstract class Behaviour for its behaviours, instead of concrete implementations of behaviour.

-
| Requirements                                                                                                            | Features (HOW) / Your Approach / Answer                                                                                                                               |
| ----------------------------------------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Must use at least two (2) classes from the engine package                                                               | (1) Yoshi extends Actor, (2) FeedAction extends Action, (3) Steak extends Item. |

| Must use/re-use at least one(1) existing feature (either from assignment 2 and/or fixed requirements from assignment 3) | Yoshi uses FollowBehaviour and AttackBehaviour, Toad sells Steak                                                                                                                                                                    |
| Must use existing or create new abstractions (e.g., abstract or interface, apart from the engine code)                  | Steak implements Sellable                                                                                                                                                                      |
| Must use existing or create new capabilities                                                                            | Status.FED, Status.FLY                                                                                                                                                                    |
---

# Requirement 5

(for group of 3 ONLY -- please remove it before submission)

**Title**:
Extra items and effects

**Description**:
New item: StinkBug - basically a mine, deals damage to Actors that step on it
New item: AuraPotion - Consumable that grants the ability to deal damage passively to all enemies around
New item: FreezePotion - Consumable that grants attacks a chance to freeze an enemy, making them unable to move or attack


**Explanation why it adheres to SOLID principles** (WHY):

- SRP: The new items have their own separate classes which manage each item as if they were separate entities.
- OCP: ConsumeAction along with Consumable handles every consumable object's consumption effects. There is no need to modify ConsumeAction if another new Consumable item is made.
- ISP: Separate interfaces Sellable and Consumable handle the feature of items that are sold and those that can be consumed.

| Requirements                                                                                                            | Features (HOW) / Your Approach / Answer                                                                                                                               |
| ----------------------------------------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Must use at least two (2) classes from the engine package                                                               | the items extend Item, ConsumeAction extends Action, StinkBug uses DropItemAction  |

| Must use/re-use at least one(1) existing feature (either from assignment 2 and/or fixed requirements from assignment 3) | the potions are Sellable and Consumable, Freeze effect uses AttackAction  |                                                                                                                                                                      |
| Must use existing or create new abstractions (e.g., abstract or interface, apart from the engine code)                  | the items implement Sellable, Consumable                                                                                                                                                                      |
| Must use existing or create new capabilities                                                                            | Status.FREEZE, Status.AURA                                                                                                                                                                      |
