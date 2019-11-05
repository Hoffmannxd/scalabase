import Dependencies._

lazy val rootProjectName   = "scalabase"
lazy val toolKitModuleName = "toolkit"

lazy val scala211               = "2.11.12"
lazy val scala212               = "2.12.10"
lazy val scala213               = "2.13.1"
lazy val supportedScalaVersions = List(scala211, scala212, scala213)

def scalacOptionsVersion(scalaVersion: String): Seq[String] =
  CrossVersion.partialVersion(scalaVersion) match {
    case Some((2L, major)) if major == 13L => Seq("-Ymacro-annotations")
    case _                                 => Seq.empty
  }

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
      scalaVersion := scala213,
      //crossScalaVersions := List(scala213),//supportedScalaVersions,
      version := "0.0.1",
      organization := "me.mhoffmann",
      organizationName := "Hoffmann",
      startYear := Some(2019),
      licenses := List("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
      homepage := Some(url("https://me.hoffmann")),
      developers := List(
        Developer(id = "hoffmannxd", name = "Matheus Hoffmann", email = "", url = url("https://github.com/hoffmannxd"))
      ),
      libraryDependencies ++= Seq(
        compilerPlugin(Lib.kindProjector),
        compilerPlugin(Lib.betterMonadicFor)
      ) ++ Lib.CIRIS,
      Compile / unmanagedSourceDirectories := Seq((Compile / scalaSource).value),
      Test / unmanagedSourceDirectories := Seq((Test / scalaSource).value),
      resolvers ++= Seq(
        Resolver.defaultLocal,
        Resolver.mavenLocal,
        Resolver.mavenCentral,
        Classpaths.typesafeReleases,
        Classpaths.sbtPluginReleases
      )
    )
    .settings(scalacOptions := scalacOptionsVersion(scalaVersion.value))
    .enablePlugins(AssemblyPlugin, AutomateHeaderPlugin, BuildInfoPlugin, TpolecatPlugin)
