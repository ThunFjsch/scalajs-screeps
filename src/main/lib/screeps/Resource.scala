package screeps

import scala.scalajs.js

@js.native
trait Resource extends RoomObject {
  /** The amount of resource units containing */
  val amount: Int

  /** A unique object identificator. You can use Game.getObjectById method to retrieve an object instance by its id. */
  val id: String

  /** One of the RESOURCE_* constants. */
  val resourceType: String

}
