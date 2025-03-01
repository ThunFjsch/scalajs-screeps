package screepsTypes

import scala.scalajs.js
import scala.scalajs.js.annotation._

@js.native
@JSGlobalScope
object Memory extends Memory

@js.native
trait Memory extends js.Object {
  var creeps: js.Dictionary[CreepMemory]= js.native
  var spawns: js.Dictionary[SpawnMemory] = js.native
  var rooms:  js.Dictionary[RoomMemory] = js.native
  var powerCreeps:  js.Dictionary[PowerCreepMemory] = js.native
  var flags:  js.Dictionary[FlagMemory] = js.native
}
@js.native
trait CreepMemory extends js.Object{
  var role: String = js.native
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