package screepsTypes

import scala.scalajs.js

/**
 * A destroyed structure.
 *
 * This is a walkable object.
 *
 * Usually decays in 500 ticks except some special cases.
 */
@js.native
trait Ruin extends RoomObject {
    /**
     * A unique object identifier.
     * You can use {@link Game.getObjectById} to retrieve an object instance by its id.
     */
    val id: String = js.native
    /** Time of destruction. */
    val destroyTime: Int = js.native
    /** An object with the ruin contents. */
    val store: Store[true] = js.native
    /** The amount of game ticks before this ruin decays. */
    val ticksToDecay: Int = js.native
    /** An object containing the destroyed structure. */
    val structure: Structure
}