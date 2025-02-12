enablePlugins(ScalaJSPlugin)

name := "Scala.js screeps bot"
scalaVersion := "2.13.14" // or a newer version such as "3.4.2", if you like
Compile / scalaSource := baseDirectory.value / "src/main"
// This is an application with a main method
scalaJSUseMainModuleInitializer := true