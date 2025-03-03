package screepsBot

import screepsTypes._
import scala.scalajs.js

// @js.annotation.JSExportTopLevel("cache")
object Cache extends Cache

trait Cache {
    var ownedRooms: js.Dictionary[RoomMemory] = js.Dictionary.empty
}