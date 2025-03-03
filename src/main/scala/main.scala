package screepsBot

import screepsTypes._

import scala.scalajs.js
import scala.scalajs.js.annotation._
import scala.scalajs.js.Dynamic.{global => g}
import js.special.delete

// object Setup {
//   @JSExportTopLevel("main")
//   def runSetup(): Unit = {
//   }
// }

object Screeps {
  MemoryHack.register()
  
  var cache = Cache
  if(cache.ownedRooms != js.Dictionary.empty){
    Game.rooms.asInstanceOf[js.Dictionary[Room]].map(room =>  {
      if(js.typeOf(room) != "undefined" && room._2.controller.my){
        cache.ownedRooms.addOne(room._1, room._2.memory)
      }
    })
  }
  cache.ownedRooms.foreach(o => g.console.log(o._1))
  
  if(js.isUndefined(js.Dynamic.global.Memory.rooms.asInstanceOf[js.Dictionary[RoomMemory]])){
  js.Dynamic.global.Memory.rooms = js.Dictionary.empty
  }
  if(js.isUndefined(js.Dynamic.global.Memory.creeps.asInstanceOf[js.Dictionary[CreepMemory]])){
    js.Dynamic.global.Memory.creeps = js.Dictionary.empty
  }
  if(js.isUndefined(js.Dynamic.global.Memory.spawns.asInstanceOf[js.Dictionary[CreepMemory]])){
    js.Dynamic.global.Memory.spawns = js.Dictionary.empty
  }

  @JSExportTopLevel("loop")
  def loop(): Unit = {

    MemoryHack.runHack()
    cache.ownedRooms.foreach(room => {g.console.log(room._1)})
    
    val num = numOfRole(Game.creeps.values, "harvester")
    // g.console.log("Harvesters: " + num)
    val numOfUpgrader: Int = numOfRole(Game.creeps.values, "upgrader")
    // g.console.log("Upgraders: " + numOfUpgrader)
    if (num < 2 && Game.spawns("Spawn1").spawning == null && Game.time % 3 == 1) {
      val newName = Game.spawns("Spawn1").createCreep(js.Array("work", "carry", "move"), "", new js.Object{ val role = "harvester"; val state = false})
      g.console.log("Spawning new harvester: " + newName)
    } else if(numOfUpgrader < 4 && Game.spawns("Spawn1").spawning == null && Game.time % 3 == 1){
      val newName = Game.spawns("Spawn1").createCreep(js.Array("work", "carry", "move"), "", new js.Object{ val role = "upgrader"; val state = false})
      g.console.log("Spawning new upgrader: " + newName)
    }
    
    cleanMemory()
    
    // Executes the logic of all existing creeps
    Creeps.run(Game.creeps)

    Game.structures.values.filter(_.structureType == StructureConstant.Tower.toString).map(_.asInstanceOf[StructureTower]).foreach(tower)
  }

  def numOfRole(creeps: Iterable[Creep], name: String) = {
    creeps.count(creep => {
      val creepDynamic = creep.asInstanceOf[js.Dynamic]
      val role = creepDynamic.memory.role
      js.typeOf(role) != "undefined" && (role.asInstanceOf[String] == name)
    })
  }

  def cleanMemory(): Unit = {
    if(js.typeOf(js.Dynamic.global.Memory.creeps) != "undefined" && (Game.time % 150) == 1){
      val creepsMemory = js.Dynamic.global.Memory.creeps.asInstanceOf[js.Dictionary[js.Any]]
      for ((name, _) <- creepsMemory) {
        if (js.isUndefined(js.Dynamic.global.Game.creeps.selectDynamic(name))) {
          delete(creepsMemory, name)
        }
      }
    }
  }

  /**
    * Sadly StructureController has hits and hitsMax, but they are undefined, so we need to resort to trickery
    */
  val healingOpts = new js.Object {
    def filter(s: js.Dynamic): Boolean =
      !js.isUndefined(s.hits) && !js.isUndefined(s.hitsMax) && (s.hits.asInstanceOf[Int] < s.hitsMax.asInstanceOf[Int])
  }

  def tower(tower: StructureTower): Unit = {
    Option(tower.pos.findClosestByRange(FindType.HostileCreeps.id)).asInstanceOf[Option[Creep]].foreach(tower.attack)
    Option(tower.pos.findClosestByRange(FindType.Structures.id,healingOpts)).asInstanceOf[Option[Structure]].foreach(tower.repair)
  } 
}


