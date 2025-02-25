package screepsTypes

import scala.scalajs.js
import scala.scalajs.js.|

/**
 * Power Creeps are immortal "heroes" that are tied to your account and can be respawned in any PowerSpawn after death.
 *
 * You can upgrade their abilities ("powers") up to your account Global Power Level (see {@link Game.gpl}).
 *
 * |                   |                  |
 * | ----------------- | ---------------- |
 * | **Time to live**  | 5,000
 * | **Hits**          | 1,000 per level
 * | **Capacity**      | 100 per level
 *
 * [Full list of available powers](https://docs.screeps.com/power.html#Powers)
 */
@js.native
trait PowerCreep extends js.Object{
    val carry: Store[false] = js.native
    val carryCapacity: Int = js.native
    val className: String = js.native
    val deleteTime: js.UndefOr[Int] = js.native
    val hits: Int = js.native
    val hitsMax: Int = js.native
    val id: String = js.native
    val level: Int = js.native
    val memory: PowerCreepMemory = js.native
    val my: Boolean = js.native
    val name: String = js.native
    val owner: Owner = js.native
    val store: Store[false] = js.native
    val powers: js.Dictionary[PowerCreepPowers] = js.native
    val saying: String = js.native
    val shard: js.UndefOr[String] = js.native
    val spawnCoolDownTime: js.UndefOr[Int] = js.native
    val ticksToLive: js.UndefOr[Int] = js.native
    def cancelOrder(methodName: String): Short = js.native
    def delete(cancel: Option[Boolean]): Short = js.native
    def drop(resourceType: String, amount: Option[Int]): Short = js.native
    def enableRoom(controller: StructureController): Short = js.native
    def move(direction: Short): Short = js.native
    def move(target: Creep): Short = js.native
    def moveByPath(path: js.Array[PathStep] | js.Array[RoomPosition] | String): Short = js.native
    def moveTo(x: Int, y: Int, opts: Option[MoveTooOpts]): Short = js.native
    def moveTo(target: Either[RoomPosition, js.Dynamic], opts: Option[MoveTooOpts]): Short = js.native
    def notifyWhenAttacked(enabled: Boolean): Short = js.native
    def pickup(target: Resource): Short = js.native
    def rename(name: String): Short = js.native
    def renew(target: Either[StructurePowerBank, StructurePowerSpawn]): Short = js.native
    def say(message: String, toPublic: Option[Boolean]): Short = js.native
    def spawn(powerSpawn: StructurePowerSpawn): Short = js.native
    def suicide(): Short = js.native
    def transfer(target: Creep | PowerCreep | Structure, resourceType: String, amount: Option[Int]): Short = js.native
    def upgrade(power: String): Short = js.native
    def usePower(power: String, target: Option[RoomObject]): Short = js.native
    def withdraw(target: Structure | Tombstone | Ruin, resourceType: String, amount: Option[Int]): Short = js.native
}

@js.native
trait PowerCreepPowers extends js.Object{
    val level: Int = js.native
    val cooldown: js.UndefOr[Int] = js.native
}