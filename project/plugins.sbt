ivyLoggingLevel := UpdateLogging.Quiet
scalacOptions in Compile ++= Seq("-feature", "-deprecation")

addSbtPlugin("io.get-coursier" % "sbt-coursier-java-6" % "1.0.0-M12-1")
addSbtPlugin("com.fommil" % "sbt-sensible" % "1.0.3")

addSbtPlugin("com.typesafe" % "sbt-mima-plugin" % "0.1.11")
