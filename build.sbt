// build.sbt
val scala3Version = "3.3.5" // Or a later Scala 3 version

lazy val root = project
  .in(file("."))
  .settings(
    name := "scala-snippet-demo",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    // Add ScalaTest dependency for testing
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.18" % Test // Use latest version
  )