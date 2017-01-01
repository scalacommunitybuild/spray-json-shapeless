ivyLoggingLevel := UpdateLogging.Quiet
scalacOptions in Compile ++= Seq("-feature", "-deprecation")

addSbtPlugin("com.fommil" % "sbt-sensible" % "1.1.3")

addSbtPlugin("com.typesafe" % "sbt-mima-plugin" % "0.1.11")
