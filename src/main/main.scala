package tutorial.webapp

import screeps.types._

import scala.scalajs.js
import scala.scalajs.js.annotation._
import scala.scalajs.js.Dynamic.{global => g}

object screepsBot {
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
        case "miner" => harvester(creep)
        // case "upgrader"  => upgrader(creep)
        case "builder"   => builder(creep)
        case unknown     => g.console.log("unknown role: " + unknown)
      }
    })

    Game.structures.values.filter(_.structureType == StructureType.Tower.toString).map(_.asInstanceOf[StructureTower]).foreach(tower)
  }

  def numOfHarvesters(creeps: Iterable[Creep]) =
    creeps.filter(_.memory.role.asInstanceOf[String] == "harvester").size

   def cleanMemory() = {
     Memory.creeps.keys.filterNot(Game.creeps.contains).foreach { name: String =>
       g.console.log("Recycling memories of " + name)
       g.console.log(Memory.creeps)
     }
  }

  def harvester(creep: Creep) = {
    if (creep.carry("energy") < creep.carryCapacity) {
      harvest(creep)
    } else {
      val opts = new js.Object {
        def filter(structure:Structure): Boolean = {
          return (structure.structureType == StructureType.Extension.toString ||
            structure.structureType == StructureType.Spawn.toString ||
            structure.structureType == StructureType.Tower.toString ) && (
            structure.asInstanceOf[OwnedStructureWithEnergy].energy < structure.asInstanceOf[OwnedStructureWithEnergy].energyCapacity)
        }
      }.asInstanceOf[js.Object]
      val targets = creep.room.find(FindType.Structures.id, opts).asInstanceOf[js.Array[Structure]]
      targets.headOption.foreach{ target =>
        if(creep.transfer(target, ResourceType.Energy.toString) == Errors.NotInRange.id ) {
          creep.moveTo(target.pos)
        }
      }
    }
  }

  def harvest(creep:Creep) = {
    val sources = creep.room.find(FindType.Sources.id).asInstanceOf[js.Array[Source]]
    if (creep.harvest(sources.head) == Errors.NotInRange.id) {
      creep.moveTo(sources(0).pos)
    }
  }

  // def upgrader(creep: Creep) = {
  //   if (creep.carry("energy") == 0) {
  //     harvest(creep)
  //   } else {
  //     if(creep.upgradeController(creep.room.controller) == Errors.NotInRange.id) {
  //       creep.moveTo(creep.room.controller.POS)
  //     }
  //   }
  // }

  def builder(creep: Creep) = {
    if (js.isUndefined(creep.memory.building))
      creep.memory.building = false

    if (creep.memory.building.asInstanceOf[Boolean] && creep.carry("energy") == 0) {
      creep.memory.building = false
      creep.say("harvesting")
    }
    if (!creep.memory.building.asInstanceOf[Boolean] && creep.carry("energy") == creep.carryCapacity) {
      creep.memory.building = true
      creep.say("building")
    }

    if (creep.memory.building.asInstanceOf[Boolean]) {
      creep.room.find(FindType.ConstructionSites.id).asInstanceOf[js.Array[ConstructionSite]].headOption.foreach { site =>
        if (creep.build(site) == Errors.NotInRange.id) {
          creep.moveTo(site.pos)
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


