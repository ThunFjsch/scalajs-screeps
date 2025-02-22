package screepsTypes

import scala.scalajs.js

/**
 * A rare resource deposit needed for producing commodities.
 *
 * Can be harvested by creeps with a WORK body part. Each harvest operation triggers a cooldown period, which becomes longer and longer over time.
 *
 * Learn more about deposits from [this article](https://docs.screeps.com/resources.html).
 *
 * |              |             |
 * | ------------ | ----------- |
 * | **Cooldown** | 0.001 * totalHarvested ^ 1.2
 * | **Decay**    | 50,000 ticks after appearing or last harvest operation
 */
@js.native
trait Deposit extends js.Object{
    /**
     * A unique object identifier.
     *
     * You can use {@link Game.getObjectById} to retrieve an object instance by its id.
     */
    val id: String = js.native
    /**
     * The deposit type, one of the {@link DepositConstant}:
     * - `RESOURCE_MIST`
     * - `RESOURCE_BIOMASS`
     * - `RESOURCE_METAL`
     * - `RESOURCE_SILICON`
     */
    val depositType: String = js.native
    /**
     * The amount of game ticks until the next harvest action is possible.
     */
    val cooldown: Int = js.native
    /**
     * The cooldown of the last harvest operation on this deposit.
     */
    val lastCooldown: Int = js.native
    /**
     * The amount of game ticks when this deposit will disappear.
     */
    val ticksToDecay: Int = js.native
}