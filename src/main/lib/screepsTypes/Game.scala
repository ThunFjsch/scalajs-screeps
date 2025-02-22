package screepsTypes

import scala.scalajs.js
import scala.scalajs.js.annotation._
import scala.scalajs.js.Dynamic._

@js.native
trait CPUShardLimits extends js.Object{
  @JSBracketAccess
  def apply(shard: String): Int = js.native
}

@js.native
trait HeapStatistics extends js.Object{
  def total_heap_size: Double = js.native
  def total_heap_size_executable: Double = js.native
  def total_physical_size: Double = js.native
  def total_available_size: Double = js.native
  def used_heap_size: Double = js.native
  def heap_size_limit: Double = js.native 
  def malloced_memory: Double = js.native
  def peak_malloced_memory: Double = js.native 
  //val does_zap_garbage: 0 | 1;
  def externally_allocated_size: Double = js.native;
}

@js.native
trait CPU extends js.Object {
  // Your assigned CPU limit for the current shard
  def limit: Int = js.native
  // An amount of available CPU time at the current game tick
  def tickLimit: Int = js.native
  // An amount of unused CPU accumulated in your bucket: http://docs.screepsTypes.com/cpu-limit.html#Bucket
  def bucket: Int = js.native
  // An object with limits for each shard with shard name 
  def shardLimits: CPUShardLimits = js.native
  // Whether full CPU is currently unlocked for your account
  def unlocked: Boolean = js.native 
  /**
    The time in milliseconds since UNIX epoch time until full CPU is unlocked for your account.
    This property is not defined when full CPU is not unlocked for your account or it's unlocked with a subscription.
  */ 
  def unlockedTime: js.UndefOr[Int] = js.native 
  /** 
    Get amount of CPU time used from the beginning of the current game tick
    Always returns 0 in the simulator
  */ 
  def getUsed() : Int
  /**
  * Allocate CPU limits to different shards.
  *
  * Total amount of CPU should remain equal to {@link Game.cpu.shardLimits}.
  * This method can be used only once per 12 hours.
  *
  * @param limits An object with CPU values for each shard in the same format as {@link Game.cpu.shardLimits}.
  * @returns One of the following codes:
  * - OK: The operation has been scheduled successfully.
  * - ERR_BUSY: 12-hours cooldown period is not over yet.
  * - ERR_INVALID_ARGS: The argument is not a valid shard limits object.
  */ 
  def setShardLimits(limits: CPUShardLimits): js.UndefOr[Short] = js.native
  /**
  * Use this method to get heap statistics for your virtual machine.
  *
  * This method will be undefined if you are not using IVM.
  *
  * The return value is almost identical to Node's [v8.getHeapStatistics()](https://nodejs.org/docs/latest-v10.x/api/v8.html#v8_v8_getheapstatistics).
  *
  * This function returns one additional property: externally_allocated_size which is the total amount of currently
  * allocated memory which is not included in the v8 heap but counts against this isolate's memory limit.
  * ArrayBuffer instances over a certain size are externally allocated and will be counted here.
  */
  def getHeapStatistics(): HeapStatistics
  /**
  * This method will be undefined if you are not using IVM.
  *
  * Reset your runtime environment and wipe all data in heap memory.
  * Player code execution stops immediately.
  */
  def halt(): Nothing = js.native
  /**
  * Generate 1 pixel resource unit for 10000 CPU from your bucket.
  *
  * @returns One of the following codes:
  * - OK: The operation has been scheduled successfully.
  * - ERR_NOT_ENOUGH_RESOURCES: Your bucket does not have enough CPU.
  */
  def generatePixel(): js.UndefOr[Either[Errors.OK.type, Errors.NotEnoughResources.type]] = js.native
  /**
  * Unlock full CPU for your account for additional 24 hours.
  *
  * This method will consume 1 CPU unlock bound to your account (See {@link Game.resources}).
  * If full CPU is not currently unlocked for your account, it may take some time (up to 5 minutes) before unlock is applied to your account.
  * @returns One of the following codes:
  * - OK: The operation has been scheduled successfully.
  * - ERR_NOT_ENOUGH_RESOURCES: Your account does not have enough cpuUnlock resource.
  * - ERR_FULL: Your CPU is unlocked with a subscription.
  */
  def unlock(): js.UndefOr[Short] = js.native
}

trait GlobalControlLevel extends js.Object {
  /** The current level. */
  def level: Int
  /** The current progress to the next level. */
  def progress: Int
  /** The progress required to reach the next level. */
  def progressTotal: Int
}

@js.native
trait GlobalPowerLevel extends js.Object {
  def level: Int = js.native
  def progress: Int = js.native
  def progressTotal: Int = js.native
}

/**
  * The main game object containing all the gameplay information. The object is accessible via the global Game variable.
  */
@js.native
@JSGlobal
object Game extends js.Object {
  /** An Object containing information about your CPU usage */
  def cpu: CPU = js.native
  /** A hash containing all your creeps with creep names as hash keys */
  def creeps: js.Dictionary[Creep] = js.native
  /** A hash containing all your flags with flag names as hash keys */
  val flags: js.Dictionary[Flag] = js.native
  /** Your global control level */
  val gcl: GlobalControlLevel = js.native
  /** Your global power level */
  val gpl: GlobalPowerLevel = js.native
  /** A global Object representing world GameMap */
  val map: GameMap = js.native
  /** A global object representing the in-game market*/
  val market: Market = js.native
  /**
     * A hash containing all your power creeps with their names as hash keys.
     *
     * Even power creeps not spawned in the world can be accessed here.
     */
  val powerCreeps: js.Dictionary[PowerCreep] = js.native
  /**
     * An object with your global resources that are bound to the account, like pixels or cpu unlocks.
     *
     * Each object key is a resource constant, values are resources amounts.
     */
  val resources: js.Dictionary[js.Any] = js.native
  /**
     * A hash containing all the rooms available to you with room names as hash keys.
     *
     * A room is visible if you have a creep or an owned structure in it.
     */
  val rooms: js.Dictionary[Room] = js.native
  /** A hash containing all your spawns with spawn names as hash keys. */
  val spawns: js.Dictionary[StructureSpawn] = js.native
  /** A hash containing all your structures with structure id as hash keys. */
  val structures: js.Dictionary[OwnedStructure] = js.native
  /** A hash containing all your construction sites with their id as hash keys. */
  val constructionSites: js.Dictionary[ConstructionSite] = js.native
  /** An object describing the world shard where your script is currently being executed in. */
  val shard: Shard = js.native
  /**
    * System game tick counter. It is automatically incremented on every tick.
    *
    * {code}console.log(Game.time);{code}
    */  
  val time: Int = js.native

  /**
    * Get an object with the specified unique ID. It may be a game object of any type. Only objects from the rooms which are visible to you can be accessed.
    * @param id The unique identificator.
    * @return Returns an object instance or null if it cannot be found.
    *
    * @example {{{
    *         creep.memory.sourceId = creep.pos.findClosestByRange(FIND_SOURCES).id
    *         var source = Game.getObjectById(creep.memory.sourceId);
    * }}}
    */
  def getObjectById(id: String): js.Object = js.native

  /**
    * Send a custom message at your profile email. This way, you can set up notifications to yourself on any occasion
    * within the game. You can schedule up to 20 notifications during one game tick.
    * Not available in the Simulation Room.
    *
    * @param message Custom text which will be sent in the message. Maximum length is 1000 characters.
    * @param groupInterval If set to 0 (default), the notification will be scheduled immediately. Otherwise, it will
    *                      be grouped with other notifications and mailed out later using the specified time in minutes.
    */
  def notify(message: String, groupInterval: Int): Unit = js.native
}