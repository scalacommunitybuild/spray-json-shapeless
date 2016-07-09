addSbtPlugin("org.scalariform" % "sbt-scalariform" % "1.6.0")

addSbtPlugin("io.get-coursier" % "sbt-coursier-java-6" % "1.0.0-M12-1")

scalacOptions in Compile ++= Seq("-feature", "-deprecation")

addSbtPlugin("com.typesafe" % "sbt-mima-plugin" % "0.1.9")
