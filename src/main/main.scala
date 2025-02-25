package screepsBot

import screepsTypes._

import scala.scalajs.js
import scala.scalajs.js.annotation._
import scala.scalajs.js.Dynamic.{global => g}
import screepsTypes.{OwnedStructureWithStorage, Creep, Memory, FindType, StructureConstant, ResourceType, StructureTower, Game, Structure, ConstructionSite, Errors, Source}

object screeps {
  def main(args: Array[String]): Unit = {
    loop
  }

  @JSExport
  def loop = {
    val num = numOfHarvesters(Game.creeps.values)
    g.console.log("Harvesters: " + num)
    if (num < 2) {
      val newName = Game.spawns("Spawn1").createCreep(js.Array("work", "carry", "move"), "", new js.Object{ val role = "harvester"})
      g.console.log("Spawning new harvester: " + newName)
    }
    // cleanMemory()

    Game.creeps.values.map( creep => {
      creep.memory.role.asInstanceOf[String] match {
        case "harvester" => harvester(creep)
        case "upgrader"  => upgrader(creep)
        case "builder"   => builder(creep)
        case unknown     => g.console.log("unknown role: " + unknown)
      }
    cleanMemory()

    Game.structures.values.filter(_.structureType == StructureConstant.Tower.toString).map(_.asInstanceOf[StructureTower]).foreach(tower)
  }

  def numOfHarvesters(creeps: Iterable[Creep]) =
    creeps.filter(_.memory.role.asInstanceOf[String] == "harvester").size

   def cleanMemory(): Unit = {
     Memory.creeps.keys.filterNot(Game.creeps.contains).foreach { name: String =>
       g.console.log("Recycling memories of " + name)
       g.console.log(Memory.creeps)
     }
  }

  def harvester(creep: Creep) = {
    g.console.log(creep.carryCapacity)
    g.console.log(creep.store.getUsedCapacity("energy"))
    if (creep.store.getUsedCapacity("energy") < creep.carryCapacity) {
      g.console.log("gees luise")
      harvest(creep)
    } else {
      val opts = new js.Object {
        def filter(structure:Structure): Boolean = {
          return (structure.structureType == StructureConstant.Extension.toString ||
            structure.structureType == StructureConstant.Spawn.toString ||
            structure.structureType == StructureConstant.Tower.toString ) && (
            structure.asInstanceOf[OwnedStructureWithStorage].energy < structure.asInstanceOf[OwnedStructureWithStorage].energyCapacity)
        }
      }.asInstanceOf[js.Object]
      val targets = creep.room.find(FindType.Structures.id, opts).asInstanceOf[js.Array[Structure]]
      targets.headOption.foreach{ target =>
        if(creep.transfer(target, ResourceType.Energy.toString) == Errors.NotInRange.id ) {
          creep.moveToPosition(target.pos)
        }
      }
    }
  }

  def harvest(creep:Creep) = {
    val sources = creep.room.find(FindType.Sources.id).asInstanceOf[js.Array[Source]]
    g.console.log(sources.head)
    g.console.log(creep.harvest(sources.head))
    if (creep.harvest(sources.head) == Errors.NotInRange.id) {
      creep.moveToPosition(sources(0).pos)
    }
  }

  def upgrader(creep: Creep) = {
    if (creep.store.getCapacity("energy") == 0) {
      harvest(creep)
    } else {
      if(creep.upgradeController(creep.room.controller) == Errors.NotInRange.id) {
        creep.moveToPosition(creep.room.controller.pos)
      }
    }
  }

  def builder(creep: Creep) = {
    if (js.isUndefined(creep.memory.building))
      creep.memory.building = false

    if (creep.memory.building.asInstanceOf[Boolean] && creep.store.getCapacity("energy") == 0) {
      creep.memory.building = false
      creep.say("harvesting")
    }
    if (!creep.memory.building.asInstanceOf[Boolean] && creep.store.getCapacity("energy") == creep.carryCapacity) {
      creep.memory.building = true
      creep.say("building")
    }

    if (creep.memory.building.asInstanceOf[Boolean]) {
      creep.room.find(FindType.ConstructionSites.id).asInstanceOf[js.Array[ConstructionSite]].headOption.foreach { site =>
        if (creep.build(site) == Errors.NotInRange.id) {
          creep.moveToPosition(site.pos)
        }
      }
    } else {
      harvest(creep)
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


