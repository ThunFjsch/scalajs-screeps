package screepsTypes

object Direction extends Enumeration {
  val Top, TopRight, Right, BottomRight, Bottom, BottomLeft, Left, TopLeft = Value
}

object BodyPartConstant extends Enumeration {
  val Move = Value("move")
  val Work = Value("work")
  val Carry = Value("carry")
  val Attack = Value("attack")
}

object Errors extends Enumeration {
  val OK                 = Value(0)
  val NotOwner           = Value(-1)
  val NoPath             = Value(-2)
  val NameExists         = Value(-3)
  val Busy               = Value(-4)
  val NotFound           = Value(-5)
  val NotEnoughResources = Value(-6)
  val InvalidTarget      = Value(-7)
  val Full               = Value(-8)
  val NotInRange         = Value(-9)
  val InvalidArgs        = Value(-10)
  val Tired              = Value(-11)
  val NoBodypart         = Value(-12)
  val RCLNotEnough       = Value(-14)
  val GCLNotEnough       = Value(-15)
}

object GameMode extends Enumeration {
  val Simulation = Value("simulation")
  val Survival   = Value("survival")
  val World      = Value("world")
  val Arena      = Value("arena")
}

object BuildableStructureConstant extends Enumeration {
  val Extension  = Value("extension")
  val Rampart    = Value("rampart")
  val Road       = Value("road")
  val Spawn      = Value("spawn")
  val Link       = Value("link")
  val Wall       = Value("constructedWall")
  val Storage    = Value("storage")
  val Tower      = Value("tower")
  val Observer   = Value("observer")
  val PowerSpawn = Value("powerSpawn")
  val Extractor  = Value("extractor")
  val Lab        = Value("lab")
  val Terminal   = Value("terminal")
  val Container  = Value("container")
  val Nuker      = Value("nuker")
  val Factory    = Value("factory")
}

object StructureConstant extends Enumeration{
  val KeeperLair = Value("keeperLair")
  val Controller = Value("controller")
  val PowerBank = Value("powerBank")
  val Portal = Value("portal")
  val InvaderCore = Value("invaderCore")
  // TODO: How to not copy but extend BuildableStructureConstant
  val Extension  = Value("extension")
  val Rampart    = Value("rampart")
  val Road       = Value("road")
  val Spawn      = Value("spawn")
  val Link       = Value("link")
  val Wall       = Value("constructedWall")
  val Storage    = Value("storage")
  val Tower      = Value("tower")
  val Observer   = Value("observer")
  val PowerSpawn = Value("powerSpawn")
  val Extractor  = Value("extractor")
  val Lab        = Value("lab")
  val Terminal   = Value("terminal")
  val Container  = Value("container")
  val Nuker      = Value("nuker")
  val Factory    = Value("factory")
}

object Color extends Enumeration(1) {
  val Red    = Value("red")
  val Purple = Value("purple")
  val Blue   = Value("blue")
  val Cyan   = Value("cyan")
  val Green  = Value("green")
  val Yellow = Value("yellow")
  val Orange = Value("orange")
  val Brown  = Value("brown")
  val Grey   = Value("grey")
  val White  = Value("white")
}

object FindType extends Enumeration {
  val ExitTop                  = Value(Direction.Top.id, "EXIT_TOP")
  val ExitRight                = Value(Direction.Right.id, "EXIT_RIGHT")
  val ExitBottom               = Value(Direction.Bottom.id, "EXIT_BOTTOM")
  val ExitLeft                 = Value(Direction.Left.id, "EXIT_LEFT")
  val Exit                     = Value(10, "EXIT")
  val Creeps                   = Value(101, "CREEPS")
  val MyCreeps                 = Value(102, "MY_CREEPS")
  val HostileCreeps            = Value(103, "HOSTILE_CREEPS")
  val SourcesActive            = Value(104, "SOURCES_ACTIVE")
  val Sources                  = Value(105, "SOURCES")
  val DroppedResources         = Value(106, "DROPPED_RESOURCES")
  val Structures               = Value(107, "STRUCTURES")
  val MyStructures             = Value(108, "MY_STRUCTURES")
  val HostileStructures        = Value(109, "HOSTILE_STRUCTURES")
  val Flags                    = Value(110, "FLAGS")
  val ConstructionSites        = Value(111, "CONSTRUCTION_SITES")
  val MySpawns                 = Value(112, "MY_SPAWNS")
  val HostileSpawns            = Value(113, "HOSTILE_SPAWNS")
  val MyConstructionSites      = Value(114, "MY_CONSTRUCTION_SITES")
  val HostileConstructionSites = Value(115, "HOSTILE_CONSTRUCTION_SITES")
  val Minerals                 = Value(116, "MINERALS")
  val Nukes                    = Value(117, "NUKES")
  val Tombstones               = Value(118, "TOMBSTONES")
  val PowerCreeps              = Value(119, "POWER_CREEPS")
  val MyPowerCreeps            = Value(120, "MY_POWER_CREEPS")
  val HostilePowerCreeps       = Value(121, "HOSTILE_POWER_CREEPS")
  val Deposits                 = Value(122, "DEPOSITS")
  val Ruins                    = Value(123, "RUINS")
}

object LookType extends Enumeration {
  val Creeps            = Value("creep")
  val Energy            = Value("energy")
  val Resources         = Value("resource")
  val Sources           = Value("source")
  val Minerals          = Value("mineral")
  val Structures        = Value("structure")
  val Flags             = Value("flag")
  val ConstructionSites = Value("constructionSite")
  val Nukes             = Value("nuke")
  val Terrain           = Value("terrain")
}

object ResourceType extends Enumeration {
  val Energy = Value("energy")
  val Power = Value("power")
  val OPS = Value("ops")

  val Biomass = Value("biomass")
  val Metal = Value("metal")
  val Mist = Value("mist")
  val Silicon = Value("silicon")

  val Utrium = Value("U")
  val Lemergium = Value("L")
  val Keanium = Value("K")
  val Zynthium = Value("Z")
  val Oxygen = Value("O")
  val Hydrogen = Value("H")
  val Catalyst = Value("X")
 
  val Hydroxie = Value("OH")
  val ZynthiumKeanite = Value("ZK")
  val UtriumLemergite = Value("UL")
  val Ghodium = Value("G")

  val UtriumHydride = Value("UH")
  val UtriumOxide = Value("UO")
  val KeaniumHydride = Value("KH")
  val KeaniumOxide = Value("KO")
  val LemergiumHydrive = Value("LH")
  val LemergiumOxide = Value("LO")
  val ZynthiumHydride = Value("ZH")
  val ZynthiumOxide = Value("ZO")
  val GhodiumHydride = Value("GH")
  val GhodiumOxide = Value("GO")

  val UtriumAcide = Value("UH2O")
  val UtriumAlkalide = Value("UHO2")
  val KeaniumAcid = Value("KH2O")
  val KeaniumAlkalide = Value("KHO2")
  val LemergiumAcid = Value("LH2O")
  val LemergiumAlkalide = Value("LHO2")
  val ZynthiumAcid = Value("ZH2O")
  val ZynthiumAlkalide = Value("ZHO2")
  val GhodiumAcid = Value("GH2O")
  val GhodiumAlkalide = Value("GHO2")

  val CatalyzedUtriumAcid = Value("XUH2O")
  val CatalyzedUtriumAlkalide = Value("XUHO2")
  val CatalyzedKeaniumAcid = Value("XKH2O")
  val CatalyzedKeaniumAlkalide = Value("XKHO2")
  val CatalyzedLemergiumAcid = Value("XLH2O")
  val CatalyzedLemergiumAlkalide = Value("XLHO2")
  val CatalyzedZynthiumAcide = Value("XZH2O")
  val CatalyzedZynthiumAlkalide = Value("XZHO2")
  val CatalyzedGhodiumAcid = Value("XGH2O")
  val CatalyzedGhodiumAlkalide = Value("XGHO2")

  val UtriumBar = Value("utrium_bar")
  val LenrgiumBar = Value("lemergium_bar")
  val ZynthiumBar = Value("zynthium_bar")
  val KeaniumBar = Value("keanium_bar")
  val GhodiumMelt = Value("ghodium_melt")
  val Oxidant = Value("oxidant")
  val Reductant = Value("reductant")
  val Purifier = Value("purifier")
  val Battery = Value("battery")

  val Composite = Value("composite")
  val Crystal = Value("crystal")
  val Liquid = Value("liquid")

  val Wire = Value("wire")
  val Switch = Value("switch")
  val Transistor = Value("transistor")
  val Microchip = Value("microchip")
  val Circuit = Value("circuit")
  val Device = Value("device")

  val Cell = Value("cell")
  val Phlegm = Value("phlegm")
  val Tissue = Value("tissue")
  val Muscle = Value("muscle")
  val Organoid = Value("organoid")
  val Organism = Value("organism")

  val Alloy = Value("alloy")
  val Tube = Value("tube")
  val Fixtures = Value("fixtures")
  val Frame = Value("frame")
  val Hydralics = Value("hydralics")
  val Machine = Value("machine")

  val Condensate = Value("condensate")
  val Concentrate = Value("concentrate")
  val Extract = Value("extract")
  val Spirit = Value("spirit")
  val Emanation = Value("emanation")
  val Essense = Value("essence")
}

object DensityConstant extends Enumeration{
  val Low = Value(1)
  val Mid = Value(2)
  val High = Value(3)
  val Ultra = Value(4)
}

object InterShardResourceConstant extends Enumeration{
  val SubscriptionToken = Value("token")
  val CpuUnlock = Value("cpuUnlock")
  val Pixel = Value("pixel") 
  val AccessKey = Value("access")
}