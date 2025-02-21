package screepsTypes

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
trait RouteStep extends js.Object {
  val exit: Int = js.native
  val room: String = js.native
}

/**
 * An object representing the room in which your units and structures are in.
 *
 * It can be used to look around, find paths, etc.
 *
 * Every object in the room contains its linked Room instance in the {@link RoomObject.room} property.
 */
@js.native
trait Room extends js.Object {
  /** The Controller structure of this room, if present, otherwise undefined. */
  val controller: StructureController = js.native
  /** Total amount of energy available in all spawns and extensions in the room. */
  val energyAvailable: Int = js.native
  /** Total amount of energyCapacity of all spawns and extensions in the room. */
  val energyCapacityAvailable: Int = js.native
  /** Returns an array of events happened on the previous tick in this room. */
  def getEventLog(raw: Boolean): Either[js.Array[js.Object], String] = js.native
  /**
  * The room's memory.
  *
  * A shorthand to `Memory.rooms[room.name]`. You can use it for quick access the room’s specific memory data object.
  */
  // TODO: Add type for Memory segments, Room/Creep/.../
  val memory: js.Dynamic = js.native
  /** The name of the room. */
  val name: String = js.native
  /** The {@link StructureStorage} of this room, if present, otherwise undefined. */
  val storage: js.UndefOr[StructureStorage] = js.native
  /** The {@link StructureTerminal} of this room, if present, otherwise undefined. */
  val terminal: js.UndefOr[StructureTerminal] = js.native
  /**
    * A {@link RoomVisual} object for this room.
    *
    * You can use this object to draw simple shapes (lines, circles, text labels) in the room.
    */
  val visual: RoomVisual = js.native
  /**
    * Create new {@link ConstructionSite} at the specified location.
    * @param x The X position
    * @param y The Y position
    * @param structureType One of {@link BuildableStructureConstant Buildable STRUCTURE_*}.
    * @returns
    * - OK: The operation has been scheduled successfully.
    * - ERR_NOT_OWNER: The room is claimed or reserved by a hostile player.
    * - ERR_INVALID_TARGET: The structure cannot be placed at the specified location.
    * - ERR_FULL: You have too many construction sites. The maximum number of construction sites per player is 100.
    * - ERR_INVALID_ARGS: The location is incorrect.
    * - ERR_RCL_NOT_ENOUGH: Room Controller Level insufficient.
    */
  def createConstructionSite(x: Int, y: Int, structureType: String, name: Option[String]): Short = js.native
  /**
     * Create new {@link ConstructionSite} at the specified location.
     * @param pos Can be a {@link RoomPosition} object or any object containing RoomPosition.
     * @param structureType One of {@link BuildableStructureConstant Buildable STRUCTURE_*}.
     * @returns
     * - OK: The operation has been scheduled successfully.
     * - ERR_NOT_OWNER: The room is claimed or reserved by a hostile player.
     * - ERR_INVALID_TARGET: The structure cannot be placed at the specified location.
     * - ERR_FULL: You have too many construction sites. The maximum number of construction sites per player is 100.
     * - ERR_INVALID_ARGS: The location is incorrect.
     * - ERR_RCL_NOT_ENOUGH: Room Controller Level insufficient.
     */
  def createConstructionSite(pos: RoomPosition, structureType: String, name: Option[String]): Short = js.native
  /**
     * Create new {@link Flag} at the specified location.
     * @param x The X position.
     * @param y The Y position.
     * @param name (optional) The name of a new flag.
     *
     * It should be unique, i.e. the Game.flags object should not contain another flag with the same name (hash key).
     *
     * If not defined, a random name will be generated.
     *
     * The maximum length is 60 characters.
     * @param color The color of a new flag. Should be one of the COLOR_* constants. The default value is COLOR_WHITE.
     * @param secondaryColor The secondary color of a new flag. Should be one of the COLOR_* constants. The default value is equal to color.
     * @returns The name of a new flag, or one of the following error codes:
     * - ERR_NAME_EXISTS: There is a flag with the same name already.
     * - ERR_FULL: You have too many flags. The maximum number of flags per player is 10000.
     * - ERR_INVALID_ARGS: The location or the name or the color constant is incorrect.
     */
  def createFlag(
    pos: Either[RoomPosition, js.Object], 
    name: String = js.native, 
    color: Int = js.native, 
    secondaryColor: Int = js.native
  ): js.Any = js.native
  /**
     * Find all objects of the specified type in the room.
     * @param type One of the {@link FindConstant FIND_*} constants.
     * @param opts An object with additional options
     * @returns An array with the objects found.
     */
  // Todo flesh out opts
  def find(findType: Int, opts: js.UndefOr[js.Object] = js.undefined): js.Array[js.Object] = js.native
/**
     * Find the exit direction en route to another room.
     * @param room Another room name or room object.
     * @returns The room direction constant, one of the following: FIND_EXIT_TOP, FIND_EXIT_RIGHT, FIND_EXIT_BOTTOM, FIND_EXIT_LEFT
     * Or one of the following error codes: ERR_NO_PATH, ERR_INVALID_ARGS
     */
  def findExitTo(room: Either[String, Room]): Short = js.native
  /**
     * Find an optimal path inside the room between fromPos and toPos using A* search algorithm.
     * @param fromPos The start position.
     * @param toPos The end position.
     * @param opts (optional) An object containing additional pathfinding flags
     * @returns An array with path steps
     */
  def findPath(fromPos: RoomPosition, toPos: RoomPosition, opts: Option[FindPathOpts]): js.Array[PathStep] = js.native/**
     * Creates a {@link RoomPosition} object at the specified location.
     * @param x The X position.
     * @param y The Y position.
     * @returns A RoomPosition object or null if it cannot be obtained.
     */
  def getPositionAt(x: Int, y: Int): Either[RoomPosition, Nothing] = js.native
  /**
     * Get a {@link RoomTerrain} object which provides fast access to static terrain data.
     *
     * This method works for any room in the world even if you have no access to it.
     */
  def getTerrain(): RoomTerrain = js.native
  /**
     * Get the list of objects at the specified room position.
     * @param x The X position.
     * @param y The Y position.
     * @returns An array with objects at the specified position
     * @throws if `x` or `y` are out of bounds.
     */
  def lookAt(x: Int, y: Int): js.Array[js.Object] = js.native
  /**
     * Get the list of objects at the specified room position.
     * @param target Can be a RoomPosition object or any object containing RoomPosition.
     * @returns An array with objects at the specified position
     */
  def lookAt(target: RoomPosition): js.Array[js.Object] = js.native
  /**
     * Get the list of objects at the specified room area.
     *
     * This method is more CPU efficient in comparison to multiple {@link Room.lookAt} calls.
     * @param top The top Y boundary of the area.
     * @param left The left X boundary of the area.
     * @param bottom The bottom Y boundary of the area.
     * @param right The right X boundary of the area.
     * @param asArray Set to true if you want to get the result as a plain array.
     * @returns An object with all the objects in the specified area
     */
  def lookAtArea(top: Int, left: Int, bottom: Int, right: Int, asArray: Option[Boolean]): js.Array[js.Object] = js.native
  /**
     * Get the objects at the given position.
     *
     * @param type One of the {@link LookConstant LOOK_*} constants.
     * @param x The X position.
     * @param y The Y position.
     * @returns An array of objects of the requested type at the given position, or ERR_INVALID_ARGS.
     */
  def lookForAt(lookType: String, x: Int, y: Int): js.Array[js.Object] = js.native
  /**
     * Get the objects at the given position.
     *
     * @param type One of the {@link LookConstant LOOK_*} constants.
     * @param target A RoomPosition. Its room name will be ignored.
     * @returns An array of objects of the requested type at the given position, or ERR_INVALID_ARGS.
     */
  def lookForAt(lookType: String, target: RoomPosition): js.Array[js.Object] = js.native
  /**
     * Get the given objects in the supplied area.
     * @param type One of the {@link LookConstant LOOK_*} constants
     * @param top The top (Y) boundary of the area.
     * @param left The left (X) boundary of the area.
     * @param bottom The bottom (Y) boundary of the area.
     * @param right The right(X) boundary of the area.
     * @param asArray Flatten the results into an array?
     * @returns Either an array of found objects with an x & y property or a nested object keyed by x, then y coordinate.
     */
  def lookForAtArea(
    lookType: String, 
    top: Int, 
    left: Int, 
    bottom: Int, 
    right: Int, 
    asArray: Option[Boolean]
  ): js.Any = js.native
}
