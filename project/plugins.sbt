// https://github.com/sbt/sbt-scalariform/issues/20
// the version of org.scalariform will be bumped by ensime-sbt
addSbtPlugin("org.scalariform" % "sbt-scalariform" % "1.5.1")

// sbt-coveralls needs a new release
// https://github.com/scoverage/sbt-coveralls/issues/52
//addSbtPlugin("org.scoverage" %% "sbt-scoverage" % "1.2.0")
//addSbtPlugin("org.scoverage" %% "sbt-coveralls" % "1.0.0")

scalacOptions in Compile ++= Seq("-feature", "-deprecation")

