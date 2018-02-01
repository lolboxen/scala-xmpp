name := "scala-xmpp"

version := "1.0.6"

scalaVersion := "2.11.4"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.4.2",
  "com.typesafe.akka" %% "akka-slf4j" % "2.4.2",
  "org.scala-lang.modules" %% "scala-xml" % "1.0.3",
  "com.fasterxml" % "aalto-xml" % "0.9.9",
  "commons-io" % "commons-io" % "2.4",
  "com.typesafe.akka" %% "akka-testkit" % "2.4.2" % "test",
  "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test",
  "org.scalamock" %% "scalamock-scalatest-support" % "3.2" % "test"
)