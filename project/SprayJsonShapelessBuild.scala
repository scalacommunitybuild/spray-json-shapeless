// Copyright 2016 Sam Halliday
// Licence: http://www.apache.org/licenses/LICENSE-2.0
import sbt._
import Keys._
import SonatypeSupport._

object SprayJsonShapelessBuild extends Build {

  lazy override val settings = super.settings ++ Seq(
    scalaVersion := "2.11.7",
    organization := "com.github.fommil",
    version := "1.2.1-SNAPSHOT"
  )

  lazy val root = Project("root", file(".")).settings(
    Sensible.settings ++ sonatype("fommil", "spray-json-shapeless", Apache2)
  ).settings(
    name := "spray-json-shapeless",
    updateOptions := updateOptions.value.withCachedResolution(true),
    libraryDependencies ++= Seq(
      "io.spray" %% "spray-json" % "1.3.2",
      "org.slf4j" % "slf4j-api" % Sensible.logbackVersion
    ) ++ Sensible.shapeless(scalaVersion.value) ++ Sensible.testLibs()
  )

}
