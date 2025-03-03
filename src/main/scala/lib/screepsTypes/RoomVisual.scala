package screepsTypes

import scala.scalajs.js
import scala.scalajs.js.annotation._
import scala.scalajs.js.|

/**
 * Room visuals provide a way to show various visual debug info in game rooms.
 *
 * You can use the RoomVisual object to draw simple shapes that are visible only to you.
 * Every existing Room object already contains the visual property, but you also can create new RoomVisual objects for any room (even without visibility) using the constructor.
 *
 * Room visuals are not stored in the database, their only purpose is to display something in your browser.
 * All drawings will persist for one tick and will disappear if not updated. All RoomVisual API calls have no added CPU cost (their cost is natural and mostly related to simple `JSON.serialize` calls).
 * However, there is a usage limit: you cannot post more than 500 KB of serialized data per one room (see {@link RoomVisual.getSize} method).
 *
 * All draw coordinates are measured in game coordinates and centered to tile centers, i.e. (10,10) will point to the center of the creep at x:10; y:10 position.
 * Fractional coordinates are allowed.
 */
@js.native
trait RoomVisual extends js.Object{
    /** The name of the room. */
    val roomName: String = js.native
    /**
     * Draw a line.
     * @param x1 The start X coordinate.
     * @param y1 The start Y coordinate.
     * @param x2 The finish X coordinate.
     * @param y2 The finish Y coordinate.
     * @param style The (optional) style.
     * @returns The RoomVisual object, for chaining.
     */
    def line(x1: Int, y1: Int, x2: Int, y2: Int, style: Option[LineStyle]): RoomVisual = js.native
    /**
     * Draw a line.
     * @param pos1 The start position object.
     * @param pos2 The finish position object.
     * @param style The (optional) style.
     * @returns The RoomVisual object, for chaining.
     */
    def line(pos1: RoomPosition, pos2: RoomPosition, style: Option[LineStyle]): RoomVisual = js.native
    /**
     * Draw a circle.
     * @param x The X coordinate of the center.
     * @param y The Y coordinate of the center.
     * @param style The (optional) style.
     * @returns The RoomVisual object, for chaining.
     */
    def circle(x: Int, y: Int, style: Option[CircleStyle]): RoomVisual = js.native
    /**
     * Draw a circle.
     * @param pos The position object of the center.
     * @param style An object describing the style.
     * @returns The RoomVisual object itself, so that you can chain calls.
     */
    def circle(pos: RoomPosition, style: Option[CircleStyle]): RoomVisual = js.native
    /**
     * Draw a rectangle.
     * @param x The X coordinate of the top-left corner.
     * @param y The Y coordinate of the top-left corner.
     * @param w The width of the rectangle.
     * @param h The height of the rectangle.
     * @param style The (optional) style.
     * @returns The RoomVisual object, for chaining.
     */
    def rect(x: Int, y: Int, w: Float, h: Float, style: Option[PolyStyle]): RoomVisual = js.native
    /**
     * Draw a line.
     * @param topLeftPos The position object of the top-left corner.
     * @param width The width of the rectangle.
     * @param height The height of the rectangle.
     * @param style An object describing the style.
     * @returns The RoomVisual object itself, so that you can chain calls.
     */
    def rect(topLeftPos: RoomPosition, width: Float, height: Float, style: Option[PolyStyle]): RoomVisual = js.native
    /**
     * Draw a polygon.
     * @param points An array of points. Every array item should be either an array with 2 numbers (i.e. [10,15]), or a RoomPosition object.
     * @param style The (optional) style.
     * @returns The RoomVisual object, for chaining.
     */
    def poly(point: Either[js.Array[js.Object], js.Array[RoomPosition]], style: Option[PolyStyle]): RoomVisual = js.native
    /**
     * Draw a text label.
     * @param text The text message.
     * @param x The X coordinate of the label baseline center point.
     * @param y The Y coordinate of the label baseline center point.
     * @param style The (optional) text style.
     * @returns The RoomVisual object, for chaining.
     */
    def text(text: String, x: Int, y: Int, style: Option[TextStyle]): RoomVisual = js.native
    /**
     * Draw a text label.
     *
     * You can use any valid Unicode characters, including emoji.
     * @param text The text message.
     * @param pos The position object of the center.
     * @param style An object describing the style.
     * @returns The RoomVisual object itself, so that you can chain calls.
     */
    def text(text: String, pos: RoomPosition, style: Option[TextStyle]): RoomVisual = js.native
    /**
     * Remove all visuals from the room.
     * @returns The RoomVisual object, for chaining.
     */
    def clear(): RoomVisual = js.native
    /**
     * Get the stored size of all visuals added in the room in the current tick.
     * It must not exceed 512,000 (500 KB).
     * @returns The size of the visuals in bytes.
     */
    def getSize(): Int = js.native
    /**
     * Returns a compact representation of all visuals added in the room in the current tick.
     * @returns A string with visuals data. There's not much you can do with the string besides store them for later.
     */
    @JSName("export")
    def handOut(): String = js.native
    /**
     * Add previously exported (with {@link RoomVisual.export}) room visuals to the room visual data of the current tick.
     * @param data The string returned from `RoomVisual.export`.
     * @returns The RoomVisual object itself, so that you can chain calls.
     */
    @JSName("import")
    def require(data: String): RoomVisual = js.native
}

@js.native
trait LineStyle extends js.Object{
    /**
     * Line width.
     * @default 0.1
     */
    val width: Option[Float] = js.native
    /**
     * Line color in any web format.
     * @default #ffffff (white)
     */
    val color: Option[String] = js.native
    /**
     * Opacity value.
     * @default 0.5
     */
    val opacity: Option[Float] = js.native
    /**
     * Either undefined (solid line), dashed, or dotted.
     * @default undefined
     */
    val lineStyle: js.UndefOr[String] = js.native // "dashed" | "dotted" | "solid" | undefined
}

@js.native
trait PolyStyle extends js.Object{
    /**
     * Fill color in any web format.
     * @default undefined (no fill).
     */
    val fill: Option[String] = js.native
    /**
     * Opacity value, default is 0.5.
     */
    val opacity: Option[Int] = js.native
    /**
     * Stroke color in any web format.
     * @default #ffffff (white)
     */
    val stroke: Option[String] = js.native
    /**
     * Stroke line width.
     * @default 0.1
     */
    val strokeWidth: Option[Float] = js.native
    /**
     * Either undefined (solid line), dashed, or dotted.
     * @default undefined
     */
    val lineStyle: js.UndefOr[String] = js.native
}

@js.native
trait CircleStyle extends js.Object{
    /**
     * Circle radius.
     * @default 0.15
     */
    val radius: Option[Float] = js.native
}

@js.native
trait TextStyle extends js.Object{
    /**
     * Font color in any web format.
     * @default #ffffff (white)
     */
    val color: Option[String] = js.native
    /**
     * Either a number or a string in one of the following forms:
     * - 0.7 - relative size in game coordinates
     * - 20px - absolute size in pixels
     * - 0.7 serif
     * - bold italic 1.5 Times New Roman
     */
    val font: Option[Either[String, Float]] = js.native
    /**
     * Stroke color in any web format.
     * @default undefined (no stroke)
     */
    val stroke: Option[js.UndefOr[String]] = js.native
    /**
     * Stroke width.
     * @default 0.15
     */
    val strokeWidth: Option[Float] = js.native
    /**
     * Background color in any web format.
     * When background is enabled, text vertical align is set to middle (default is baseline).
     * @default undefined (no background)
     */
    val backgroundColor: Option[js.UndefOr[String]] = js.native
    /**
     * Background rectangle padding
     * @default 0.3
     */
    val backgroundPadding: Option[Float] = js.native
    val align: Option[String] = js.native   // "center" | "left" | "right"
    /**
     * Opacity value.
     * @default 1.0
     */
    val opacity: Option[Float] = js.native
}