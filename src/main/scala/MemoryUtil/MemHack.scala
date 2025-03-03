/**
 * Ripped from https://github.com/AlinaNova21/ZeSwarm/
 * Organized by Carson Burke and xTwisteDx
 *
 * At point of writing, this "exploit" is not an official feature nor a violation of the TOS.
 * It has officially been implictly endorsed by o4 as he has shared the code publically
 *
 * Usage:
 * register MemoryHack
 * import MemoryHack into main file
 * At start of loop, run MemoryHack
 * 
 * More Info @
 * https://wiki.screepspl.us/MemHack/
 */

package screepsBot

import screepsTypes._
import scala.scalajs.js
import js.special.delete
import screepsBot.Screeps.cache
import screepsTypes.Game.creeps


object MemoryHack{
    // @js.annotation.JSExportTopLevel("cachedMemory")
    var cachedMemory: js.Dynamic = null
    def register(): Unit = {
        this.cachedMemory = js.Dynamic.global.Memory
        this.cachedMemory = RawMemory._parsed   
    }

    def runHack(): Unit = {
        for ((key, value) <- cachedMemory.asInstanceOf[js.Dictionary[Memory]]) {
            js.Dynamic.global.Memory.updateDynamic(key)(value)
        }
        RawMemory._parsed = js.JSON.parse(js.JSON.stringify(cachedMemory))
    }
}