ThisBuild / version := "0.1.0-SNAPSHOT"

val scalatestVersion = "3.2.15"
val h2Version = "2.1.214"

lazy val scala211 = "2.11.12"
lazy val scala212 = "2.12.17"
lazy val scala213 = "2.13.10"
ThisBuild / crossScalaVersions := List(scala213, scala212, scala211)
ThisBuild / releaseCrossBuild := true
ThisBuild / scalaVersion := scala213
Test / fork := true


lazy val root = (project in file("."))
  .settings(
    name := "crosserror"
  )

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % scalatestVersion % Compile,
  "com.h2database" % "h2" % h2Version % Compile
)

