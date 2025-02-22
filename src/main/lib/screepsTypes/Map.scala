package screepsTypes

import scala.scalajs.js
import scala.scalajs.js.annotation._
import scala.scalajs.js.Dynamic._

@js.native
trait ExitsInformation extends js.Object{
  @JSBracketAccess
  def apply(roomName: String): String = js.native
}

@js.native
trait RoomStatusPermanent extends js.Object {
  val status: String //"normal" | "closed"
  val timestamp: Null
}

@js.native
trait RoomStatusTemporary extends js.Object {
  val status: String //"novice" | "respawn"
  val timestamp: Double
}

@js.native
trait RouteOptions extends js.Object {
  /**
    * This can be used to calculate the cost of entering that room.
    * You can use this to do things like prioritize your own rooms, or avoid some rooms.
    * You can return a floating point cost or Infinity to block the room.
    *
    * @param roomName the room we are considering on the route
    * @param fromRoomName the room from which we are leaving on the route
    * @return a floating point cost or Infinity to block the room.
    */
  def routeCallback(roomName: String, fromRoomName: String): Float
}


/** An object representing the world map. Use it to navigate between rooms.
  * The object is accessible via Game.map property.
  */
@js.native
trait GameMap extends js.Object {
  /**
    * List all exits available from the room with the given name.
    * @param roomName The room name.
    * @return The exits information in the following format, or null if the room not found.
    *         {{{   {
    *   "1": "W8N4",    // TOP
    *   "3": "W7N3",    // RIGHT
    *   "5": "W8N2",    // BOTTOM
    *   "7": "W9N3"     // LEFT
    * } }}}
    * @note CPU Cost: LOW
    */
  def describeExits(roomName: String): js.UndefOr[ExitsInformation] = js.native
  /**
    * Find the exit direction from the given room en route to another room.
    *
    * @param fromRoom Start room name or room object.
    * @param toRoom Finish room name or room object.
    * @param opts The route finding options. See [findRoute] and [RouteOptions].
    * @return The room direction constant, one of the following:
    *           Top, Right, Bottom, Left
    *         Or one of the following error codes:
    *           NoPath, InvalidArgs
    * @note CPU Cost: HIGH
    */
  def findExit(fromRoom: String, toRoom: String, opts: RouteOptions): Short = js.native
  /**
    * Find route from the given room to another room.
    * @param fromRoom Start room name or room object.
    * @param toRoom Finish room name or room object.
    * @param opts The route finding options. See [findRoute] and [RouteOptions].
    * @return The route array or NoPath
    */
  // Note: return should be js.Array[RoutePart]
  def findRoute(fromRoom: Either[String, Room], toRoom: Either[String, Room], opts: RouteOptions): js.Any = js.native
  /**
    * Get the linear distance (in rooms) between two rooms. You can use this function to estimate
    * the energy cost of sending resources through terminals, or using observers and nukes.
    * @param roomName1 the name of the first room
    * @param roomName2 the name of the second room
    * @return the number of rooms between the given two rooms
    */
  def getRoomLinearDistance(roomName1: String, roomName2: String, continuous: Boolean = false): Int = js.native
  /**
    * Get terrain type at the specified room position. This method works
    * for any room in the world even if you have no access to it.
    * @param x X position in the room.
    * @param y Y position in the room.
    * @param roomName The room name.
    * @return One of the following string values:
    *         "plain", "swamp", "wall
    */
    // TODO: Maybe replace string with a type that says that only plain swamp wall can be returned?
  def getTerrainAt(x: Int, y: Int, roomName: String): String = js.native
  def getTerrainAt(pos: RoomPosition): String = js.native
  /**
     * Get room terrain for the specified room.
     *
     * This method works for any room in the world even if you have no access to it.
     * @param roomName String name of the room.
     */
  def getRoomTerrain(roomName: String): RoomTerrain = js.native
  /**
     * Returns the world size as a number of rooms between world corners.
     *
     * For example, for a world with rooms from W50N50 to E50S50 this method will return 102.
     */
  def getWorldSize(): Int = js.native
  /**
     * Check if the room is available to move into.
     * @param roomName The room name.
     * @returns A boolean value.
     * @deprecated Use `Game.map.getRoomStatus` instead
     */
  def isRoomAvailable(roomName: String): Boolean = js.native
  /**
     * Get the room status to determine if it's available, or in a reserved area.
     * @param roomName The room name.
     * @returns A {@link RoomStatus} object.
     */
  def getRoomStatus(roomName: String): Either[RoomStatusPermanent, RoomStatusTemporary] = js.native
  /**
     * Map visuals provide a way to show various visual debug info on the game map.
     * You can use the {@link Game.map.visual} object to draw simple shapes that are visible only to you.
     *
     * Map visuals are not stored in the database, their only purpose is to display something in your browser.
     * All drawings will persist for one tick and will disappear if not updated.
     * All `Game.map.visual` calls have no added CPU cost (their cost is natural and mostly related to simple JSON.serialize calls).
     * However, there is a usage limit: you cannot post more than 1000 KB of serialized data.
     *
     * All draw coordinates are measured in global game coordinates (`RoomPosition`).
     */
  val visual: MapVisual = js.native
}

/**
 * Map visuals provide a way to show various visual debug info on the game map.
 *
 * You can use the {@link Game.map.visual} object to draw simple shapes that are visible only to you.
 *
 *  Map visuals are not stored in the database, their only purpose is to display something in your browser.
 * All drawings will persist for one tick and will disappear if not updated.
 * All `Game.map.visual` calls have no added CPU cost (their cost is natural and mostly related to simple `JSON.serialize` calls).
 * However, there is a usage limit: you cannot post more than 1000 KB of serialized data.
 *
 * All draw coordinates are measured in global game coordinates ({@link RoomPosition}).
 */
@js.native
trait MapVisual extends js.Object{
  /**
     * Draw a line.
     * @param pos1 The start position object.
     * @param pos2 The finish position object.
     * @param style The optional style
     * @returns The MapVisual object, for chaining.
     */
  def line(pos1: RoomPosition, pos2: RoomPosition, style: MapLineStyle): MapVisual = js.native
  /**
     * Draw a circle.
     * @param pos The position object of the center.
     * @param style The optional style
     * @returns The MapVisual object, for chaining.
     */
  def circle(pos: RoomPosition, style: MapCircleStyle): MapVisual = js.native
  /**
     * Draw a rectangle.
     * @param topLeftPos The position object of the top-left corner.
     * @param width The width of the rectangle.
     * @param height The height of the rectangle.
     * @param style The optional style
     * @returns The MapVisual object, for chaining.
     */
  def rect(topLeftPos: RoomPosition, width: Float, height: Float, style: js.UndefOr[MapPolyStyle]): MapVisual = js.native
  /**
     * Draw a polyline.
     * @param points An array of points. Every item should be a `RoomPosition` object.
     * @param style The optional style
     * @returns The MapVisual object, for chaining.
     */
  def poly(points: Array[RoomPosition], style: js.UndefOr[MapPolyStyle]): MapVisual = js.native
  /**
     * Draw a text label. You can use any valid Unicode characters, including emoji.
     * @param text The text message.
     * @param pos The position object of the label baseline.
     * @param style The optional style
     * @returns The MapVisual object, for chaining
     */
  def text(msg: String, pos: RoomPosition, style: js.UndefOr[MapTextStyle]): MapVisual = js.native
  /**
     * Remove all visuals from the map.
     * @returns The MapVisual object, for chaining
     */
  def clear(): MapVisual = js.native
  /**
     * Get the stored size of all visuals added on the map in the current tick.
     *
     * It must not exceed 1024,000 (1000 KB).
     * @returns The size of the visuals in bytes.
     */
  def getSize(): Int = js.native
  /**
     * Returns a compact representation of all visuals added on the map in the current tick.
     * @returns A string with visuals data. There's not much you can do with the string besides store them for later.
     */
   @JSName("export")
  def handOut(): String = js.native
  /**
     * Add previously exported (with {@link Game.map.visual.export}) map visuals to the map visual data of the current tick.
     * @param data The string returned from `Game.map.visual.export`.
     * @returns The MapVisual object itself, so that you can chain calls.
     */
  @JSName("import")
  def require(data: String): MapVisual = js.native
}


@js.native
trait MapLineStyle extends js.Object {
  /**
     * Line width.
     * @default 0.1
     */
  val width: Option[Int] = js.native
  /**
     * Line color in the following format: #ffffff (hex triplet).
     * @default #ffffff
     */
  val color: Option[String] = js.native
  /**
     * Opacity value
     * @default 0.5
     */
  val opacity: Option[Float] = js.native 
  /**
     * Either undefined (solid line), dashed, or dotted.
     * @default undefined.
     */
  val lineStyle: Option[js.UndefOr[String]] = js.native 
}

@js.native
trait MapPolyStyle extends js.Object{
  /**
     * Fill color in the following format: #ffffff (hex triplet).
     * @default undefined (no fill).
     */
  def fill: Option[js.UndefOr[String]] = js.native
  /**
     * Stroke color in the following format: #ffffff (hex triplet).
     * @default #ffffff
     */
  def opacity: Option[String] = js.native
  /**
     * Stroke color in the following format: #ffffff (hex triplet).
     * @default #ffffff
     */
  def stroke: Option[String] = js.native
  /**
     * Stroke line width.
     * @default 0.5
     */
  def strokeWidth: Option[Float] = js.native
  /**
     * Either undefined (solid line), dashed, or dotted.
     * @default is undefined
     */
  def lineStyle: Option[js.UndefOr[String]] = js.native
}

@js.native
trait MapCircleStyle extends MapPolyStyle {
  /**
     * Circle radius.
     * @default 10
     */
  val radius: Float
}

@js.native
trait MapTextStyle extends js.Object {
  /**
     * Font color in the following format: #ffffff (hex triplet).
     * @default #ffffff
     */
  def color: Option[String] = js.native
  /**
     * The font family.
     * @default sans-serif
     */
  def fontFamily: Option[String] = js.native
  /**
     * The font size in game coordinates.
     * @default 10
     */
  def fontSize: Option[Int] = js.native
  /**
     * The font style ('normal', 'italic' or 'oblique')
     */
  def fontStyle: Option[Int] = js.native
  /**
     * The font variant ('normal' or 'small-caps')
     */
  def fontVariant: Option[String] = js.native
  /**
     * Stroke color in the following format: #ffffff (hex triplet)
     * @default undefined (no stroke).
     */
  def stroke: Option[js.UndefOr[String]] = js.native
  /**
     * Stroke width.
     * @default 0.15
     */
  def strokeWidth: Option[Int] = js.native
  /**
     * Background color in the following format: #ffffff (hex triplet).
     *
     * When background is enabled, text vertical align is set to middle (default is baseline).
     * @default undefined (no background).
     */
  def backgroundColor: Option[js.UndefOr[String]] = js.native
  /**
     * Background rectangle padding.
     * @default 2
     */
  def backgroundPadding: Option[Int] = js.native
  /**
     * Text align, either center, left, or right.
     * @default center
     */
  def align: Option[String] = js.native
  /**
     * Opacity value.
     * @default 0.5
     */
  def opacity: Option[Int] = js.native
}