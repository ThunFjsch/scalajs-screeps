package screeps

import scala.scalajs.js

import scala.scalajs.js.annotation._

@js.native
@JSGlobal("Room")
object Room extends js.Object {
  @JSName("serializePath")
  def serializePath(path: js.Array[RouteStep]): String = js.native

  @JSName("deserializePath")
  def deserializePath(path: String): js.Array[RouteStep] = js.native
}
@js.native
trait Room extends js.Object {
  val controller: js.UndefOr[StructureController] = js.native
  val energyAvailable: Int = js.native
  val energyCapacityAvailable: Int = js.native
  val memory: js.Dynamic = js.native
  val mode: String = js.native
  val name: String = js.native
  val storage: js.UndefOr[StructureStorage] = js.native
  val terminal: js.UndefOr[StructureTerminal] = js.native

  @JSName("createConstructionSite")
  def createConstructionSite(x: Int, y: Int, structureType: String): Short = js.native

  @JSName("createFlag")
  def createFlag(x: Int, y: Int, name: String = js.native, color: Int = js.native, secondaryColor: Int = js.native): js.Any = js.native

  @JSName("find")
  def find(findType: Int, opts: js.UndefOr[js.Object] = js.undefined): js.Array[js.Object] = js.native

  @JSName("findExitTo")
  def findExitTo(room: String): Int = js.native

  @JSName("findPath")
  def findPath(fromPos: RoomPosition, toPos: RoomPosition, opts: js.UndefOr[js.Object] = js.undefined): js.Array[PathStep] = js.native

  @JSName("getPositionAt")
  def getPositionAt(x: Int, y: Int): RoomPosition = js.native

  @JSName("lookAt")
  def lookAt(x: Int, y: Int): js.Array[js.Object] = js.native
  @JSName("lookAt")
  def lookAt(target: RoomPosition): js.Array[js.Object] = js.native

  @JSName("lookAtArea")
  def lookAtArea(top: Int, left: Int, bottom: Int, right: Int, asArray: js.UndefOr[Boolean] = js.undefined): js.Any = js.native

  @JSName("lookForAt")
  def lookForAt(lookType: String, x: Int, y: Int): js.Array[js.Object] = js.native
  @JSName("lookForAt")
  def lookForAt(lookType: String, target: RoomPosition): js.Array[js.Object] = js.native

  @JSName("lookForAtArea")
  def lookForAtArea(lookType: String, top: Int, left: Int, bottom: Int, right: Int, asArray: js.UndefOr[Boolean] = js.undefined): js.Any = js.native
}
