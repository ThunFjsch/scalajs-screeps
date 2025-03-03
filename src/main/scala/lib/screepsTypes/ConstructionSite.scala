package screepsTypes

import scala.scalajs.js

/**
 * A site of a structure which is currently under construction.
 *
 * A construction site can be created using the 'Construct' button at the left of the game field or the {@link Room.createConstructionSite} method.
 *
 * To build a structure on the construction site, give a worker creep some amount of energy and perform {@link Creep.build} action.
 *
 * You can remove enemy construction sites by moving a creep on it.
 */
@js.native
trait ConstructionSite extends RoomObject {

  /** A unique object identificator. You can use Game.getObjectById method to retrieve an object instance by its id. */
  val id: String = js.native

  /** Whether this is your own construction site. */
  val my: Boolean = js.native

  /** The site's owner */
  val owner: Owner = js.native

  /** The current construction progress. */
  val progress: Int = js.native

  /** The total construction progress needed for the structure to be built. */
  val progressTotal: Int = js.native

  /** One of the STRUCTURE_* constants. */
  val structureType: String = js.native

  /** Remove the construction site. */
  def remove(): Int = js.native
}
