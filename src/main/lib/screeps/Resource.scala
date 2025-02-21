package screepsTypes

import scala.scalajs.js

/**
 * A dropped piece of resource.
 *
 * It will decay after a while if not picked up.
 * Dropped resource pile decays for `ceil(amount/1000)` units per tick.
 */
@js.native
trait Resource extends RoomObject {
  /** The amount of resource units containing */
  val amount: Int
  /** A unique object identificator. You can use Game.getObjectById method to retrieve an object instance by its id. */
  val id: String
  /** One of the RESOURCE_* constants. */
  val resourceType: String
}