package screeps

import scala.scalajs.js

@js.native
trait PathStep extends js.Object {
  val x: Int = js.native
  val y: Int = js.native
  val dx: Int = js.native
  val dy: Int = js.native
  val direction: Int = js.native
}
