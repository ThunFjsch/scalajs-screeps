package screepsTypes

import scala.scalajs.js

/**
  * A mineral deposit. Can be harvested by creeps with a WORK body part using the extractor structure.
  * Learn more about minerals from [[http://support.screepsTypes.com/hc/en-us/articles/207891075-Minerals this article]].
  *
  * Regeneration amount    140-280K for Hydrogen and Oxygen
  *                        70-140K for Utrium, Keanium, Lemergium, Zynthium, Catalyst
  * Regeneration time      20,000 ticks
  */
@js.native
trait Mineral extends RoomObject {
  /** The prototype is stored in the Mineral.prototype global object. 
      You can use it to extend game objects behaviour globally.
  */
  // readonly protyotype: Mineral // TypeScript

  /** The density of this mineral deposit, one of the DENSITY_* constants*/
  val density: Int = js.native

  /** The remaining amount of resources. */
  val mineralAmount: Int = js.native

  /** The resource type, one of the RESOURCE_* constants. */
  val mineralType: String = js.native

  /** A unique object identificator. You can use Game.getObjectById method to retrieve an object instance by its id. */
  val id: String = js.native

  /** The remaining time after which the source will be refilled. */
  val ticksToRegeneration: Int = js.native
}
