scalaVersion in ThisBuild := "2.11.8"
organization := "com.github.fommil"
name := "spray-json-shapeless"

sonatypeGithub := ("fommil", "spray-json-shapeless")
licenses := Seq(Apache2)

libraryDependencies ++= Seq(
  "io.spray" %% "spray-json" % "1.3.2",
  "org.slf4j" % "slf4j-api" % "1.7.21"
) ++ {
  CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((2, 10)) =>
      Seq(compilerPlugin("org.scalamacros" % "paradise" % "2.0.1" cross CrossVersion.full))
    case _ => Nil
  }
} ++ Seq("com.chuusai" %% "shapeless" % "2.3.2")

mimaPreviousArtifacts := Set(organization.value %% name.value % "1.3.0")
