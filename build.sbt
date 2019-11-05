import Dependencies._

lazy val rootProjectName            = "scalabase"
lazy val toolKitModuleName          = "toolkit"

lazy val scala211               = "2.11.12"
lazy val scala212               = "2.12.10"
lazy val scala213               = "2.13.1"
lazy val supportedScalaVersions = List(scala212, scala211, scala213)

val stages = List("/compile", "/test", "/assembly", "/publishLocal")
addCommandAlias("publishAll", stages.map(s => toolKitModuleName + s).mkString(";+ ", ";+", ""))

lazy val root =
  project
    .in(file("."))
    .withId(rootProjectName)
    .aggregate(toolKit)
    .settings(
      // crossScalaVersions must be set to Nil on the aggregating project
      crossScalaVersions := Nil,
      publish / skip := true
    )

lazy val toolKit =
  project
    .in(file(toolKitModuleName))
    .withId(toolKitModuleName)
    .settings(
      crossScalaVersions := supportedScalaVersions,
      version := "0.0.1",
      organization := "me.mhoffmann",
      organizationName := "Hoffmann",
      startYear := Some(2019),
      licenses := List("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
      homepage := Some(url("https://me.hoffmann")),
      developers := List(
        Developer(id = "hoffmannxd", name = "Matheus Hoffmann", email = "", url = url("https://github.com/hoffmannxd"))
      ),
      //scalacOptions += "-Ymacro-annotations",
      libraryDependencies ++= Seq(
        compilerPlugin(Lib.kindProjector),
        compilerPlugin(Lib.betterMonadicFor)
      ),
      Compile / unmanagedSourceDirectories := Seq((Compile / scalaSource).value),
      Test / unmanagedSourceDirectories := Seq((Test / scalaSource).value)
    )
    .enablePlugins(AssemblyPlugin, AutomateHeaderPlugin, BuildInfoPlugin, TpolecatPlugin)
