name := """zarest"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  javaWs,
  "mysql" % "mysql-connector-java" % "5.1.33",
  "commons-io" % "commons-io" % "2.3", // for Files.Utils
  "org.julienrf" %% "play-jsmessages" % "1.6.2" // for javaScriptMessage properties JsMessages
)

includeFilter in (Assets, LessKeys.less) := "*.less"

excludeFilter in (Assets, LessKeys.less) := "_*.less"

