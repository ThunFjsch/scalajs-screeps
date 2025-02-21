package screeps.types

import scala.scalajs.js
import scala.scalajs.js.annotation._

@js.native
trait Owner extends js.Object {
  /** The name of the owner user. */
  val username: String = js.native
}

@js.native
trait Shard extends js.Object{
  /** The name of the shard. */
  val name: String = js.native
  /** Currently always equals to normal. */
  @JSName("type")
  val shardType: String = js.native
  /** Whether this shard belongs to the PTR. */
  val ptr: Boolean = js.native
}

