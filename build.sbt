enablePlugins(ScalaJSPlugin)

name := "Scala example Bot"
Compile / scalaSource := baseDirectory.value / "src/main"

ThisBuild / version      := "0.3"
ThisBuild / scalaVersion := "2.13.16"
ThisBuild / scalacOptions ++= Seq("-deprecation", "-feature", "-Xfatal-warnings")
ThisBuild / scmInfo := Some(ScmInfo(
        url("https://github.com/jeckhart/scalajs-screeps"),
        "scm:git:git@github.com:jeckhart/scalajs-screeps.git",
        Some("scm:git:git@github.com:jeckhart/scalajs-screeps.git")))

scalaJSLinkerConfig ~= { _.withModuleKind(ModuleKind.CommonJSModule) }
