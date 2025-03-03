package screepsBot

import screepsTypes._

import scala.scalajs.js
import scala.scalajs.js.Dynamic.{global => g}

object Creeps{
    def run(creeps: js.Dictionary[Creep]): Unit = {
      // creeps.foreach(creep => g.console.log(js.JSON.stringify(creep._2.memory)))
      creeps.values.map( creep => {
          creep.memory.role.toString() match {
              case "harvester" => harvester(creep)
              case "upgrader"  => upgrader(creep)
              case "builder"   => builder(creep)
              case unknown     => g.console.log("unknown role: " + unknown)
          }
      })
    }

  def harvester(creep: Creep) = {
    if (creep.store.getUsedCapacity("energy") < creep.carryCapacity) {
      harvest(creep)
    } else {
      val opts = new js.Object {
        def filter(structure: Structure): Boolean = {
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

  def upgrader(creep: Creep) = {
    val state = switchWorkState(creep)
    if (creep.memory.state.asInstanceOf[Boolean] == false) {
      harvest(creep)
    } else {
      if(creep.upgradeController(creep.room.controller) == Errors.NotInRange.id) {
        creep.moveToPosition(creep.room.controller.pos)
      }
    }
  }

  def builder(creep: Creep) = {
    if (js.isUndefined(creep.memory.state))
      creep.memory.state = false

    if (creep.memory.state.asInstanceOf[Boolean] && creep.store.getCapacity("energy") == 0) {
      creep.memory.state = false
      creep.say("harvesting")
    }
    if (!creep.memory.state.asInstanceOf[Boolean] && creep.store.getCapacity("energy") == creep.carryCapacity) {
      creep.memory.state = true
      creep.say("building")
    }

    if (creep.memory.state.asInstanceOf[Boolean]) {
      creep.room.find(FindType.ConstructionSites.id).asInstanceOf[js.Array[ConstructionSite]].headOption.foreach { site =>
        if (creep.build(site) == Errors.NotInRange.id) {
          creep.moveToPosition(site.pos)
        }
      }
    } else {
      harvest(creep)
    }
  }

  def harvest(creep:Creep): Unit = {
    val sources = creep.room.find(FindType.Sources.id).asInstanceOf[js.Array[Source]]
    creep.room.visual.line(creep.pos, sources.head.pos, None)
    if (creep.harvest(sources.head) == Errors.NotInRange.id) {
      creep.moveToPosition(sources(0).pos)
    }
  }

  def switchWorkState(creep: Creep): Unit = {
    if(creep.memory.state.asInstanceOf[Boolean] == true && creep.store.getUsedCapacity(ResourceType.Energy.toString()) == 0){
        creep.memory.state = false
    } 
    else if(creep.memory.state.asInstanceOf[Boolean] == false && creep.store.getUsedCapacity(ResourceType.Energy.toString()) == creep.store.getCapacity(null)){
        creep.memory.state = true
    }
  }
}