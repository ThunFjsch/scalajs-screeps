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

object MemoryHack{
    var memory: js.Dynamic = null
    def register(): Unit = {
       memory = js.Dynamic.global.Memory
       memory = RawMemory._parsed
    }

    def runHack(): Unit = {
        for ((key, value) <- memory.asInstanceOf[js.Dictionary[Memory]]) {
            js.Dynamic.global.Memory.updateDynamic(key)(value)
        }
        RawMemory._parsed = memory
    }

}