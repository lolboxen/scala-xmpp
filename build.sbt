import com.typesafe.sbt.SbtNativePackager.NativePackagerKeys._

name := "scala-xmpp"

version := "1.0"

scalaVersion := "2.11.4"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.3.7",
  "com.typesafe.akka" %% "akka-slf4j" % "2.3.7",
  "org.scala-lang.modules" %% "scala-xml" % "1.0.3",
  "com.fasterxml" % "aalto-xml" % "0.9.9",
  "org.apache.commons" % "commons-lang3" % "3.3.2",
  "commons-codec" % "commons-codec" % "1.9",
  "ch.qos.logback" % "logback-classic" % "1.1.1",
  "me.lessis" %% "retry" % "0.2.0",
  "com.typesafe.akka" %% "akka-testkit" % "2.3.7" % "test",
  "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test",
  "junit" % "junit" % "4.11" % "test"
)

bashScriptConfigLocation := Some("${app_home}/../conf/jvmopts")