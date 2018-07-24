name := """synchronize-api"""
organization := "com.synchronize"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.6"

libraryDependencies ++= Seq(
  jdbc,
  guice,
  ws,
  "org.playframework.anorm" %% "anorm" % "2.6.2"

)

scalacOptions ++= Seq(
  "-feature",
  "-unchecked",
  "-deprecation",
  "-Xlint",
  "-Ywarn-inaccessible",
  "-Ywarn-nullary-override",
  "-Ywarn-nullary-unit",
  "-Ywarn-numeric-widen",
  "-Ywarn-value-discard",
  "-language:postfixOps",
  "-language:implicitConversions"
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.synchronize.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.synchronize.binders._"
