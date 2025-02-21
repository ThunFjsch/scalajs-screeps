package screepsTypes

import scala.scalajs.js

/**
 * A creep's final resting place.
 *
 * This is a walkable structure.
 * Will decay 5 ticks per body part of the deceased creep.
 */
@js.native
trait Tombstone extends RoomObject {
    /**
     * A unique object identifier.
     *
     * You can use {@link Game.getObjectById} to retrieve an object instance by its id.
     */
    val id: String = js.native
    /** Time of death. */
    val deathTime: Int = js.native
    /**
     * An object with the tombstone contents.
     *
     * Each object key is one of the {@link ResourceConstant RESOURCE_*} constants, values are resources amounts.
     * {@link RESOURCE_ENERGY} is always defined and equals to 0 when empty, other resources are undefined when empty.
     * You can use `_.sum(tombstone.store)` to get the total amount of contents.
     */
    val store: Store[true] = js.native
    /** The amount of game ticks before this tombstone decays. */
    val ticksToDecay: Int = js.native
    /** An object containing the deceased creep. */
    val creep: Either[Creep, PowerCreep] = js.native
}