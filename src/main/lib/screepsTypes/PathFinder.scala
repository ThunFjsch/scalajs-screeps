package screepsTypes

import scala.scalajs.js
import scala.scalajs.js.|
import scala.scalajs.js.annotation._

/**
 * Contains powerful methods for pathfinding in the game world.
 *
 * This module is written in fast native C++ code and supports custom navigation costs and paths which span multiple rooms.
 *
 * Additionally PathFinder can search for paths through rooms you can't see, although you won't be able to detect any dynamic obstacles like creeps or buildings.
 */
@js.native
trait PathFinder extends js.Object{
  /** Creates a new {@link CostMatrix} containing 0's for all positions. */
  val costMatrix: CostMatrix = js.native
  /**
     * Find an optimal path between origin and goal.
     *
     * @param origin The start position.
     * @param goal goal A RoomPosition, an object containing a RoomPosition and range or an array of either.
     * @param opts An object containing additional pathfinding flags.
     */
  def search(
      origin: RoomPosition, 
      goal: RoomPosition | PosWithRange | js.Array[RoomPosition | PosWithRange],
      opts: Option[PathFinderOpts]): PathFinderPath
  /**
     * Specify whether to use this new experimental pathfinder in game objects methods.
     * This method should be invoked every tick. It affects the following methods behavior:
     * - {@link Room.findPath}
     * - {@link RoomPosition.findPathTo}
     * - {@link RoomPosition.findClosestByPath}
     * - {@link Creep.moveTo}
     *
     * @deprecated This method is deprecated and will be removed soon.
     * @param isEnabled Whether to activate the new pathfinder or deactivate.
     */
  def use(isEnabled: Boolean): Unit
}

/**
 * An object containing:
 * path - An array of RoomPosition objects.
 * ops - Total number of operations performed before this path was calculated.
 * cost - The total cost of the path as derived from `plainCost`, `swampCost` and any given CostMatrix instances.
 * incomplete - If the pathfinder fails to find a complete path, this will be true.
 *   Note that `path` will still be populated with a partial path which represents the closest path it could find given the search parameters.
 */
@js.native
trait PathFinderPath extends js.Object{
  /** An array of {@link RoomPosition} objects. */
  val path: js.Array[RoomPosition] = js.native
  /** Total number of operations performed before this path was calculated. */
  val ops: Int = js.native
  /** The total cost of the path as derived from `plainCost`, `swampCost` and any given {@link CostMatrix} instances. */
  val cost: Int = js.native
  /**
     * If the pathfinder fails to find a complete path, this will be true.
     *
     * Note that `path` will still be populated with a partial path which represents the closest path it could find given the search parameters.
     */
  val incomplete: Boolean = js.native
}

/**
 * An object containing additional pathfinding flags.
 */
@js.native
trait PathFinderOpts extends js.Object{
  /**
     * Cost for walking on plain positions.
     * @default 1
     */
  val plainCost: Option[Int] = js.native
  /**
     * Cost for walking on swamp positions.
     * @default 5
     */
  val swampCost: Option[Int] = js.native
  /**
     * Instead of searching for a path to the goals this will search for a path away from the goals.
     *
     * The cheapest path that is out of range of every goal will be returned.
     * @default false
     */
  val flee: Option[Boolean] = js.native
  /**
     * The maximum allowed pathfinding operations.
     *
     * You can limit CPU time used for the search based on ratio 1 op ~ 0.001 CPU.
     * @default 2000
     */
  val maxOps: Option[Int] = js.native
  /**
     * The maximum allowed rooms to search.
     * @default 16 (also maximum)
     */
  val maxRooms: Option[Int] = js.native
  /**
     * The maximum allowed cost of the path returned.
     *
     * If at any point the pathfinder detects that it is impossible to find a path with a cost less than or equal to maxCost it will immediately halt the search.
     * @default Infinity
     */
  val maxCost: Option[Int] = js.native
  /**
     * Weight to apply to the heuristic in the A* formula `F = G + weight * H`.
     *
     * Use this option only if you understand the underlying A* algorithm mechanics!
     * @default 1
     */
  val heuristicWeight: Option[Int] = js.native
  /**
     * Request from the pathfinder to generate a CostMatrix for a certain room.
     *
     * The callback accepts one argument, roomName.
     * This callback will only be called once per room per search. If you are running multiple pathfinding operations in a
     * single room and in a single tick you may consider caching your {@link CostMatrix} to speed up your code.
     * Please read the {@link CostMatrix} documentation below for more information on CostMatrix.
     *
     * @param roomName The name of the room the pathfinder needs a cost matrix for.
     */
  def roomCallBack(roomName: String): Boolean | CostMatrix
}

@js.native
trait PosWithRange extends js.Object{
  val pos: RoomPosition = js.native
  val range: Int = js.native
}

/**
 * Container for custom navigation cost data.
 *
 * By default PathFinder will only consider terrain data (plain, swamp, wall) — if you need to route around obstacles such as buildings or creeps you must put them into a CostMatrix.
 *
 * Generally you will create your CostMatrix from within {@link PathFinderOpts.roomCallback}.
 * If a non-0 value is found in a room's CostMatrix then that value will be used instead of the default terrain cost.
 *
 * You should avoid using large values in your CostMatrix and terrain cost flags.
 * For example, running {@link PathFinder.search} with `{ plainCost: 1, swampCost: 5 }` is faster than running it with `{plainCost: 2, swampCost: 10 }` even though your paths will be the same.
 */
@js.native
trait CostMatrix extends js.Object{
  /**
     * Set the cost of a position in this CostMatrix.
     * @param x X position in the room.
     * @param y Y position in the room.
     * @param cost Cost of this position. Must be a whole number. A cost of 0 will use the terrain cost for that tile. A cost greater than or equal to 255 will be treated as unwalkable.
     */
  def set(x: Int, y: Int, cost: Int): Unit
  /**
     * Get the cost of a position in this CostMatrix.
     * @param x X position in the room.
     * @param y Y position in the room.
     */
  def get(x: Int, y: Int): Int = js.native
  /** Copy this CostMatrix into a new CostMatrix with the same data. */
  @JSName("clone")
  def copy(): CostMatrix = js.native
  /** Returns a compact representation of this CostMatrix which can be stored via JSON.stringify. */
  def serialize(): js.Array[Int] = js.native
}


@js.native
trait FindPathOpts extends js.Object{
  val ignoreCreeps: js.UndefOr[Boolean] = js.native
  val ignoreDestrubtibleStructures: js.UndefOr[Boolean] = js.native
  val ignoreRoads: js.UndefOr[Boolean] = js.native
  var costCallback: js.UndefOr[CostCallback] = js.undefined
  val ignore: js.UndefOr[js.Array[RoomPosition]] = js.native
  val avoid: js.UndefOr[js.Array[RoomPosition]] = js.native
  val maxOps: js.UndefOr[Int] = js.native
  val heuristicWeight: js.UndefOr[Int] = js.native
  val serialize: js.UndefOr[Boolean] = js.native
  val maxRooms: js.UndefOr[Int] = js.native
  val range: js.UndefOr[Int] = js.native
  val plainCost: js.UndefOr[Int] = js.native
  val swampCost: js.UndefOr[Int] = js.native
}

@js.native
trait MoveTooOpts extends FindPathOpts{
  val reusePath: js.UndefOr[Int] = js.native
  val serializeMemory: js.UndefOr[Boolean] = js.native
  val noPathFinding: js.UndefOr[Boolean] = js.native
  val visualizepathStyle: js.UndefOr[PolyStyle] = js.native
}

@js.native
trait PathStep extends js.Object {
  val x: Int = js.native
  val y: Int = js.native
  val dx: Int = js.native
  val dy: Int = js.native
  val direction: Int = js.native
}

trait CostCallback {
  def apply(roomName: String, costMatrix: CostMatrix): Option[CostMatrix]
}
