package screeps

import scala.scalajs.js
import scala.scalajs.js.annotation._

@js.native
@JSGlobalScope
object Memory extends GlobalMemory {
  val creeps: js.Dictionary[js.Dynamic] = js.native
  val spawns: js.Dictionary[js.Dynamic] = js.native
  val rooms:  js.Dictionary[js.Dynamic] = js.native
  val flags:  js.Dictionary[js.Dynamic] = js.native
}

trait GlobalMemory extends Memory

trait Memory extends js.Object
