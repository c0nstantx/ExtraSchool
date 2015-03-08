import de.johoop.jacoco4sbt._
import JacocoPlugin._

name := """Extra School"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  javaJdbc,
//  javaEbean,
  cache,
  javaWs,
  javaJpa.exclude("org.hibernate.javax.persistence", "hibernate-jpa-2.0-api"),
  "org.hibernate" % "hibernate-entitymanager" % "4.3.8.Final"
)

// JaCoCo code coverage settings

jacoco.settings

parallelExecution in jacoco.Config := false

jacoco.reportFormats in jacoco.Config := Seq(XMLReport("utf-8"), HTMLReport("utf-8"))

jacoco.excludes in jacoco.Config := Seq("views.*", "controllers.Reverse*", "controllers.javascript.*", "controllers.ref.*", "Routes*")
