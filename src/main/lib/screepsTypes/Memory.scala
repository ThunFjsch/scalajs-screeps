package screepsTypes

import scala.scalajs.js
import scala.scalajs.js.annotation._

@js.native
@JSGlobal
object Memory extends Memory

@js.native
trait Memory extends js.Object {
  val creeps: js.Dictionary[CreepMemory]= js.native
  val spawns: js.Dictionary[SpawnMemory] = js.native
  val rooms:  js.Dictionary[RoomMemory] = js.native
  val powerCreeps:  js.Dictionary[PowerCreepMemory] = js.native
  val flags:  js.Dictionary[FlagMemory] = js.native
}
@js.native
trait CreepMemory extends js.Object{
  val role: String = js.native
  var state: js.UndefOr[Boolean] = js.native
}
@js.native
trait FlagMemory extends js.Object
@js.native
trait PowerCreepMemory extends js.Object
@js.native
trait RoomMemory extends js.Object
@js.native
trait SpawnMemory extends js.Object