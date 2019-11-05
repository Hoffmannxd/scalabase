import Dependencies._

lazy val projectName = "scalabase"
ThisBuild / scalaVersion := "2.13.1"
ThisBuild / version := "0.0.1"
ThisBuild / organization := "me.mhoffmann"
ThisBuild / organizationName := "Hoffmann"
ThisBuild / startYear := Some(2019)
ThisBuild / licenses := List("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0"))
ThisBuild / homepage := Some(url("https://me.hoffmann"))
ThisBuild / developers := List(
  Developer(id = "hoffmannxd", name = "Matheus Hoffmann", email = "", url = url("https://github.com/hoffmannxd"))
)

val stages     = List("/compile", "/test", "/assembly", "/publishLocal")
addCommandAlias("publishAll", stages.map(s => projectName+s).mkString(";",";",";"))

lazy val root =
  project
    .in(file("."))
    .withId(projectName)
    .settings(
      name := "scalabase",
      scalacOptions += "-Ymacro-annotations",
      libraryDependencies ++= Seq(
        compilerPlugin(Lib.kindProjector),
        compilerPlugin(Lib.betterMonadicFor)
      ),
      Compile / unmanagedSourceDirectories := Seq((Compile / scalaSource).value),
      Test / unmanagedSourceDirectories := Seq((Test / scalaSource).value),
    )
    .enablePlugins(AutomateHeaderPlugin, BuildInfoPlugin, TpolecatPlugin)

