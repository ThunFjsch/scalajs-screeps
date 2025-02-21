package screeps.types

import scala.scalajs.js

/**
  * The base prototype object of all structures.
  */
@js.native
trait Structure extends RoomObject {
  /** The current amount of hit points of the structure. */
  val hits: Int = js.native
  /** The total amount of hit points of the structure. */
  val hitsMax: Int = js.native
  /** A unique object identificator. You can use Game.getObjectById method to retrieve an object instance by its id. */
  val id: String = js.native
  /**
    * The room the structure is in. 
    * If you can get an instance of a Structure, you can see it.
    * If you can see the Structure, you can see the room it's in.
    */
  val room: Room
  /** The type of the structure, from the [StructureType] constants */
  val structureType: String = js.native
  /** Destroy this structure immediately. */
  def destroy(): Int = js.native
  /** Check whether this structure can be used. If the room controller level is not enough, then this
    * method will return false, and the structure will be highlighted with red in the game.
    */
  def isActive(): Boolean = js.native
  /**
    * Toggle auto notification when the structure is under attack.
    * The notification will be sent to your account email. Turned on by default
    * @param enabled Whether to enable notification or disable
    * @return One of the following codes:
    *         OK - The operation has been scheduled successfully.
    *         NotOwner - You are not the owner of this structure.
    *         InvalidArgs - enable argument is not a boolean value.
    */
  def notifyWhenAttacked(enabled: Boolean): Int = js.native
}


@js.native
trait OwnedStructure extends Structure {
  /** Whether this is your own structure. */
  val my: Boolean = js.native
  /** An object with the structure’s owner info. */
  val owner: Owner = js.native
  /** The room the structure is in. */
  val room: Room
}

@js.native
trait OwnedStructureWithStorage extends OwnedStructure {
  val energy: Int
  /** The total amount of resources the storage can contain. */
  val energyCapacity: Int = js.native
  /**
    * An object with the storage contents. Each object key is one of the RESOURCE_* constants, values
    * are resources amounts. RESOURCE_ENERGY is always defined and equals to 0 when empty, other
    * resources are undefined when empty. You can use lodash.sum to get the total amount of contents.
    */
  val store: Store[false] = js.native
}

/**
  * Claim this structure to take control over the room.
  * The controller structure cannot be damaged or destroyed.
  * It can be addressed by Room.controller property.
  */
@js.native
trait StructureController extends OwnedStructure {
  /**
    * Whether using power is enabled in this room.
    *
    * Use {@link PowerCreep.enableRoom()} to turn powers on.
    */
  val isPowerEnabled: Boolean = js.native
  /** Current controller level, from 0 to 8. */
  val level: Short = js.native
  /** The current progress of upgrading the controller to the next level. */
  val progress: Int = js.native
  /** The progress needed to reach the next level. */
  val progressTotal: Int = js.native
  /**
    * An object with the controller reservation info if present:
    */
  val reservation: ControllerReservation = js.native
  /** How many ticks of safe mode are remaining, or undefined. */
  val safeMode: Option[Int] = js.native
  /** Safe mode activations available to use. */
  val safeModeAvailable: Short = js.native
  /** During this period in ticks new safe mode activations will be blocked, undefined if cooldown is inactive. */
  val safeModeCooldown: Option[Int] = js.native
  /** An object with the controller sign info if present */
  val sign: js.UndefOr[SignDefinition] = js.native
  /**
    * The amount of game ticks when this controller will lose one level.
    * This timer can be reset by using Creep.upgradeController.
    */
  val ticksToDowngrade: Int = js.native
  /** The amount of game ticks while this controller cannot be upgraded due to attack. */
  val upgradeBlocked: Int = js.native
  /**
    * Activate safe mode if available.
    * @returns One of the following codes:
    * - OK: The operation has been scheduled successfully.
    * - ERR_NOT_OWNER: You are not the owner of this controller.
    * - ERR_BUSY: There is another room in safe mode already.
    * - ERR_NOT_ENOUGH_RESOURCES: There is no safe mode activations available.
    * - ERR_TIRED: The previous safe mode is still cooling down, or the controller is upgradeBlocked, or the controller is downgraded for 50% plus 5000 ticks or more.
    */
  def activateSafeMode(): Short = js.native
  /**
    * Make your claimed controller neutral again.
    * @return One of the following codes:
    *         OK - The operation has been scheduled successfully.
    *         NotOwner - You are not the owner of this controller.
    */
  def unclaim(): Int = js.native
}

@js.native
trait ControllerReservation extends js.Object {
  /** The name of a player who reserved this controller. */
  val username: String = js.native
  /** The amount of game ticks when the reservation will end. */
  val ticksToEnd: Int = js.native
}

@js.native
trait SignDefinition extends js.Object{
  val username: String = js.native
  val text: String = js.native
  val time: Int = js.native
  val datetime: String = js.native // TODO: Try to understand and apply the ts type date
}

/**
 * Contains energy which can be spent on spawning bigger creeps.
 *
 * Extensions can be placed anywhere in the room, any spawns will be able to use them regardless of distance.
 */
@js.native
trait StructureExtension extends OwnedStructureWithStorage {}

/**
  * Remotely transfers energy to another Link in the same room.
  */
@js.native
trait StructureLink extends OwnedStructureWithStorage {
  /** The amount of game ticks the link has to wait until the next transfer is possible. */
  val cooldown: Int = js.native
  /**
    * Remotely transfer energy to another link at any location in the same room.
    * @param target
    * @param amount
    * @return One of the following codes:
    *         OK - The operation has been scheduled successfully.
    *         NotOwner - You are not the owner of this link.
    *         NotEnoughResources - The structure does not have the given amount of energy.
    *         InvalidTarget - The target is not a valid StructureLink object.
    *         Full - The target cannot receive any more energy.
    *         InvalidArgs - The energy amount is incorrect.
    *         Tired - The link is still cooling down.
    *         RCLNotEnough - The Room Controller Level is not enough to use this link.
    * @note CPU Cost: CONST
    */
  def transferEnergy(target: StructureLink, amount: Int): Int = js.native
}

/**
 * Non-player structure.
 *
 * Spawns NPC Source Keepers that guards energy sources and minerals in some rooms.
 * This structure cannot be destroyed.
 */
@js.native
trait StructureKeeperLair extends OwnedStructure{
  // Time until next  Source Keeper spawns
  val ticksToSpawn: Option[Int] = js.native
}

/**
  * Provides visibility into a distant room from your script.
  */
@js.native
trait StructureObserver extends OwnedStructure {
  /**
    * Provide visibility into a distant room from your script. The target room object will be available on the next tick
    * @param roomName The name of the target room.
    * @return One of the following codes:
    *         OK - The operation has been scheduled successfully.
    *         InvalidArgs - roomName argument is not a valid room name value.
    *         RCLNotEnough - The Room Controller Level is not enough to use this structure.
    * @note CPU Cost: CONST
    */
  def observeRoom(roomName: String): Int = js.native
}

/**
 * Non-player structure.
 *
 * Contains power resource which can be obtained by destroying the structure.
 * Hits the attacker creep back on each attack.
 */
@js.native
trait StructurePowerBank extends OwnedStructure{
  /**
    * The amount of power containing.
    */
  val power: Int = js.native
  /** The amount of game ticks when this structure will disappear. */
  val ticksToDecay: Int = js.native
}

@js.native
trait StructurePowerSpawn extends OwnedStructureWithStorage{
  /**
     * The amount of power containing in this structure.
     * @deprecated An alias for .store[RESOURCE_POWER].
     */
  val power: Int = js.native
  /**
    * The total amount of power this structure can contain.
    * @deprecated An alias for .store.getCapacity(RESOURCE_POWER).
    */
  val powerCapacity: Int = js.native
  /**
     * Register power resource units into your account.
     *
     * Registered power allows to develop power creeps skills. Consumes 1 power resource unit and 50 energy resource units.
     * @returns One of the following codes:
     * - OK: The operation has been scheduled successfully.
     * - ERR_NOT_OWNER: You are not the owner of this structure.
     * - ERR_NOT_ENOUGH_RESOURCES: The structure does not have enough energy or power resource units.
     * - ERR_RCL_NOT_ENOUGH: Room Controller Level insufficient to use this structure.
     */
  def processPower(): Short = js.native
}


/**
  * Blocks movement of hostile creeps, and defends your creeps and structures on the same tile.
  * Can be used as a controllable gate.
  */
@js.native
trait StructureRampart extends OwnedStructure {
  /** If false (default), only your creeps can step on the same square. If true, any hostile creeps can pass through. */
  val isPublic: Boolean = js.native
  /** The amount of game ticks when this rampart will lose some hit points. */
  val ticksToDecay: Int = js.native
  /**
    * Make this rampart public to allow other players' creeps to pass through
    * @param isPublic Whether this rampart should be public or non-public
    * @return One of the following codes:
    *         OK - The operation has been scheduled successfully.
    *         NotOwner - You are not the owner of this rampart.
    * @note CPU Cost: CONST
    */
  def setPublic(isPublic: Boolean): Int = js.native
}

/**
 * Decreases movement cost to 1.
 *
 * Using roads allows creating creeps with less `MOVE` body parts.
 */
@js.native
trait StructureRoad extends OwnedStructure{
  /** The amount of game ticks when this road will lose some hit points. */
  val ticksToDecay: Int = js.native
}

/**
  * A structure that can store huge amount of resource units. Only one structure
  * per room is allowed that can be addressed by Room.storage property.
  */
@js.native
trait StructureStorage extends OwnedStructureWithStorage {}

/**
  * Remotely attacks or heals creeps, or repairs structures. Can be targeted to any object in the room.
  * However, its effectiveness highly depends on the distance. Each action consumes energy.
  */
@js.native
trait StructureTower extends OwnedStructureWithStorage {
  /**
    * Remotely attack any creep in the room.
    * @param target The target of the attack
    * @return One of the following codes:
    *         OK - The operation has been scheduled successfully.
    *         NotEnoughResources - The tower does not have enough energy.
    *         InvalidTarget - The target is not a valid creep object.
    *         RCLNotEnough - The Room Controller Level is not enough to use this structure.
    * @note CPU Cost: CONST
    */
  def attack(target: Creep): Int = js.native
  /**
    * Remotely heal any creep in the room.
    * @param target The target creep object
    * @return One of the following codes:
    *         OK - The operation has been scheduled successfully.
    *         NotEnoughResources - The tower does not have enough energy.
    *         InvalidTarget - The target is not a valid creep object.
    *         RCLNotEnough - The Room Controller Level is not enough to use this structure.
    * @note CPU Cost: CONST
    */
  def heal(target: Creep): Int = js.native
  /**
    * Remotely repair any structure in the room.
    * @param target The target structure
    * @return One of the following codes:
    *         OK - The operation has been scheduled successfully.
    *         NotEnoughResources - The tower does not have enough energy.
    *         InvalidTarget - The target is not a valid creep object.
    *         RCLNotEnough - The Room Controller Level is not enough to use this structure.
    * @note CPU Cost: CONST
    */
  def repair(target: Structure): Int = js.native
}

/**
 * Blocks movement of all creeps.
 */
@js.native
trait StructureWall extends Structure{
  /** The amount of game ticks when the wall will disappear (only for automatically placed border walls at the start of the game). */
  val ticksToLive: Int = js.native
}

/**
 * Allows to harvest mineral deposits.
 */
@js.native
trait StructureExtractor extends OwnedStructure{
  /** The amount of game ticks until the next harvest action is possible. */
  val cooldown: Int = js.native
}

/**
  * Produces mineral compounds from base minerals and boosts creeps. Learn more about minerals from this article.
  */
@js.native
trait StructureLab extends OwnedStructureWithStorage {
  /** The amount of game ticks the lab has to wait until the next reaction is possible. */
  val cooldown: Int = js.native
  /** The amount of mineral resources containing in the lab. */
  val mineralAmount: Int = js.native
  /** The type of minerals containing in the lab. Labs can contain only one mineral type at the same time. */
  val mineralType: String = js.native
  /** The total amount of minerals the lab can contain. */
  val mineralCapacity: Int = js.native
  /**
    * Boosts creep body part using the containing mineral compound. The creep has to be at adjacent square to the lab.
    * @param creep The target creep.
    * @param bodyPartsCount The number of body parts of the corresponding type to be boosted. Body parts are always
    *                       counted left-to-right for TOUGH, and right-to-left for other types. If undefined, all the
    *                       eligible body parts are boosted.
    * @return One of the following codes:
    *         OK - The operation has been scheduled successfully.
    *         NotOwner - You are not the owner of this lab.
    *         NotFound - The mineral containing in the lab cannot boost any of the creep's body parts.
    *         NotEnoughResources - The lab does not have enough energy or minerals.
    *         InvalidTarget - The targets is not valid creep object.
    *         NotInRange - The targets are too far away.
    * @note CPU Cost: CONST
    */
  def boostCreep(creep: Creep, bodyPartsCount: Int = ???): Short = js.native
  /**
     * Unboost a creep.
     *
     * Immediately remove boosts from the creep and drop 50% of the mineral compounds used to boost it onto the ground regardless of the creep's remaining time to live.
     * The creep has to be at adjacent square to the lab.
     * Unboosting requires cooldown time equal to the total sum of the reactions needed to produce all the compounds applied to the creep.
     * @param creep The target creep.
     * @returns One of the following codes:
     * - OK: The operation has been scheduled successfully.
     * - ERR_NOT_OWNER: You are not the owner of this lab, or the target creep.
     * - ERR_NOT_FOUND: The target has no boosted parts.
     * - ERR_INVALID_TARGET: The target is not a valid Creep object.
     * - ERR_NOT_IN_RANGE: The target is too far away.
     * - ERR_TIRED: The lab is still cooling down.
     * - ERR_RCL_NOT_ENOUGH: Room Controller Level insufficient to use this structure.
     */
  def unboostCreep(creep: Creep): Short = js.native
  /**
     * Breaks mineral compounds back into reagents.
     *
     * The same output labs can be used by many source labs.
     * @param lab1 The first result lab.
     * @param lab2 The second result lab.
     * @returns One of the following codes:
     * - OK: The operation has been scheduled successfully.
     * - ERR_NOT_OWNER: You are not the owner of this lab.
     * - ERR_NOT_ENOUGH_RESOURCES: The source lab do not have enough resources.
     * - ERR_INVALID_TARGET: The targets are not valid lab objects.
     * - ERR_FULL: One of targets cannot receive any more resource.
     * - ERR_NOT_IN_RANGE: The targets are too far away.
     * - ERR_INVALID_ARGS: The reaction cannot be reversed into this resources.
     * - ERR_TIRED: The lab is still cooling down.
     * - ERR_RCL_NOT_ENOUGH: Room Controller Level insufficient to use this structure.
     */
  def reverseReaction(lab1: StructureLab, lab2: StructureLab): Short = js.native
  /**
    * Produce mineral compounds using reagents from two another labs. The same input labs can be used by many output labs
    * @param lab1 The first source lab.
    * @param lab2 The second source lab.
    * @return One of the following codes:
    *         OK - The operation has been scheduled successfully.
    *         NotOwner - You are not the owner of this lab.
    *         NotEnoughResources - The source lab does not have enough resources or minerals.
    *         InvalidTarget - The targets are not valid lab objects.
    *         Full - The target cannot receive any more energy.
    *         NotInRange - The targets are too far away.
    *         InvalidArgs - The reaction cannot be run using this resources.
    *         Tired - The lab is still cooling down.
    * @note CPU Cost: CONST
    */
  def runReaction(lab1: StructureLab, lab2: StructureLab): Int = js.native
}

/**
  * Sends any resources to a Terminal in another room. The destination Terminal can belong to any
  * player. If its storage is full, the resources are dropped on the ground. Each transaction
  * requires additional energy (regardless of the transfer resource type) according to this
  * formula: ceil(0.1 * amount * linearDistanceBetweenRooms). For example, sending 100 mineral
  * units from W1N1 to W2N3 will consume 20 energy units. You can track your incoming and outgoing
  * transactions and estimate range cost between rooms using the Game.market object. Only one
  * Terminal per room is allowed that can be addressed by Room.terminal property.
  */
@js.native
trait StructureTerminal extends OwnedStructureWithStorage {
  val cooldown: Int = js.native
  /**
    * Sends resource to a Terminal in another room with the specified name.
    * @param resourceType The type of resource to send
    * @param amount The amount of resources to be sent. The minimum amount is 100.
    * @param destination The name of the target room. You don't have to gain visibility in this room.
    * @param description The description of the transaction. It is visible to the recipient.
    *                    The maximum length is 100 characters.
    * @return One of the following codes:
    *         OK - The operation has been scheduled successfully.
    *         NotOwner - You are not the owner of this structure.
    *         NotEnoughResources - The structure does not have the required amount of resources.
    *         InvalidArgs - The arguments provided are incorrect.
    */
  def send(resourceType: String,
           amount: Int,
           destination: String,
           description: String = ""
          ): Int = js.native
}

/**
  * A small container that can be used to store resources. This is a walkable structure.
  * All dropped resources automatically goes to the container at the same tile.
  */
@js.native
trait StructureContainer extends OwnedStructureWithStorage {
  /** The amount of game ticks when this container will lose some hit points. */
  val ticksToDecay: Int = js.native
}


/**
  * Launches a nuke to another room dealing huge damage to the landing area. Each launch has a cooldown and
  * requires energy and ghodium resources. Launching creates a Nuke object at the target room position which
  * is visible to any player until it is landed. Incoming nuke cannot be moved or cancelled. Nukes cannot be
  * launched from or to novice rooms.
  *
  * Controller level
  * 1-7	—
  * 8	1 nuke
  * Cost	100,000
  * Hits	1,000
  * Range	5 rooms
  * Launch cost	200,000 energy
  * 5,000 ghodium
  * Launch cooldown	100,000 ticks
  * Landing time	50,000 ticks
  * Effect	All creeps, construction sites and dropped resources in the room are removed immediately, even inside ramparts. Damage to structures:
  *   10,000,000 hits at the landing position;
  *   1,000,000 hits at range 1;
  *   100,000 hits at range 2-4.
  */
@js.native
trait StructureNuker extends OwnedStructureWithStorage {
  /** The amount of ghodium containing in this structure. */
  val ghodium: Int = js.native
  /** The total amount of ghodium this structure can contain. */
  val ghodiumCapacity: Int = js.native
  /** The amount of game ticks until the next launch is possible. */
  val cooldown: Int = js.native
  /**
    * Launch a nuke to the specified position.
    * @param pos The target room position.
    * @return One of the following codes:
    *         OK - The operation has been scheduled successfully.
    *         NotOwner - You are not the owner of this structure.
    *         NotEnoughResources - The structure does not have enough energy and/or ghodium.
    *         InvalidTarget - The target is not a valid RoomPosition.
    *         NotInRange - The target room is out of range.
    *         Tired - The structure is still cooling down.
    *         RCLNotEnough - The Room Controller Level is not enough to use this link.
    */
  def launchNuke(pos: RoomPosition): Int = js.native
}

/**
 * A non-player structure.
 *
 * Instantly teleports your creeps to a distant room acting as a room exit tile.
 * Portals appear randomly in the central room of each sector.
 */
@js.native
trait structurePortal extends Structure{
  /**
     * The portal's destination.
     *
     * If this is an inter-room portal, then this property contains a {@link RoomPosition} object leading to the point in the destination room.
     * If this is an inter-shard portal, then this property contains an object with shard and room string properties.
     * Exact coordinates are undetermined, the creep will appear at any free spot in the destination room.
     */
  val destination: Either[RoomPosition, ShardDestination] = js.native
  /** The amount of game ticks when the portal disappears, or undefined when the portal is stable. */
  val ticksToDecay: js.UndefOr[Int] = js.native
}

trait ShardDestination {
  val shard: String
  val room: String
}

/**
 * A structure which produces trade commodities from base minerals and other commodities.
 */
@js.native
trait StructureFactory extends OwnedStructure{
  /** The amount of game ticks the factory has to wait until the next produce is possible. */
  val cooldown: Int = js.native
  /**
    * The level of the factory.
    *
    * Can be set by applying the {@link PWR_OPERATE_FACTORY} power to a newly built factory.
    * Once set, the level cannot be changed.
    */
  val level: Option[Int] = js.native
  /** An object with the structure contents. */
  val store: Store[false] = js.native
  /**
     * Produces the specified commodity.
     *
     * All ingredients should be available in the factory store.
     * @param resource One of the {@link CommoditiesTypes producible RESOURCE_*} constants.
     * @returns One of the following codes:
     * - OK: The operation has been scheduled successfully.
     * - ERR_NOT_OWNER: You are not the owner of this structure.
     * - ERR_BUSY: The factory is not operated by the PWR_OPERATE_FACTORY power.
     * - ERR_NOT_ENOUGH_RESOURCES: The structure does not have the required amount of resources.
     * - ERR_INVALID_TARGET: The factory cannot produce the commodity of this level.
     * - ERR_FULL: The factory cannot contain the produce.
     * - ERR_INVALID_ARGS: The arguments provided are incorrect.
     * - ERR_TIRED: The factory is still cooling down.
     * - ERR_RCL_NOT_ENOUGH: Your Room Controller level is insufficient to use the factory.
     */
  def produce(resource: String): Short = js.native
}

/**
 * A structure which is a control center of NPC Strongholds, and also rules all invaders in the sector.
 */
@js.native
trait StructureInvaderCore extends OwnedStructure{
  /**
    * The level of the stronghold.
    *
    * The amount and quality of the loot depends on the level.
    */
  val level: Int = js.native
  /** Shows the timer for a not yet deployed stronghold, undefined otherwise. */
  val ticksToDeploy: Int = js.native
  /** If the core is in process of spawning a new creep, this object will contain a {@link StructureSpawn.Spawning} object, or `null` otherwise. */
  val spawning: Spawning
}