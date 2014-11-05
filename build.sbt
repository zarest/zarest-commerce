name := """zarest"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  javaJdbc,
  //javaJpa,
  javaEbean,
  cache,
  javaWs,
  "mysql" % "mysql-connector-java" % "5.1.33",
  "commons-io" % "commons-io" % "2.3", // for Files.Utils
  "org.julienrf" %% "play-jsmessages" % "1.6.2"//, // for javaScriptMessage properties JsMessages
//  "org.hibernate" % "hibernate-entitymanager" % "4.3.7.Final",
//  "org.hibernate" % "hibernate-envers" % "4.3.7.Final"
)

includeFilter in(Assets, LessKeys.less) := "*.less"

excludeFilter in(Assets, LessKeys.less) := "_*.less"

