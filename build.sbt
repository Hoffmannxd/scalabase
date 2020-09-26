import Dependencies._
import sbtbuildinfo.BuildInfoKey.action

import scala.util.Try

ThisBuild / scalafixScalaBinaryVersion := "2.13"
ThisBuild / scalafixDependencies += "com.github.liancheng" %% "organize-imports" % "0.4.0"

/// Meta

lazy val buildInfoSettings = Seq(
  buildInfoKeys := Seq[BuildInfoKey](
        name,
        version,
        scalaVersion,
        sbtVersion,
        action("lastCommitHash") {
          import scala.sys.process._
          Try("git rev-parse HEAD".!!.trim).getOrElse("?")
        }
      ),
  buildInfoOptions += BuildInfoOption.BuildTime,
  buildInfoOptions += BuildInfoOption.ToJson,
  buildInfoOptions += BuildInfoOption.ToMap,
  buildInfoPackage := "me.hoffmann.version",
  buildInfoObject := "BuildInfo"
)

/// Dependencies

val AkkaVersion        = "2.6.8"
val AkkaHttpVersion    = "10.2.0"
val AkkaMetricsVersion = "1.2.0"
val TapirVersion       = "0.17.0-M1"
val PrometheusVersion  = "0.0.9"

val loggingDependencies = Seq(
  "com.typesafe.scala-logging" %% "scala-logging"  % "3.9.2",
  "ch.qos.logback"             % "logback-classic" % "1.2.3",
  "org.codehaus.janino"        % "janino"          % "3.1.2"
)

val monitoringDependencies = Seq(
  "io.prometheus" % "simpleclient"                  % PrometheusVersion,
  "io.prometheus" % "simpleclient_hotspot"          % PrometheusVersion,
  "fr.davit"      %% "akka-http-metrics-core"       % AkkaMetricsVersion,
  "fr.davit"      %% "akka-http-metrics-prometheus" % AkkaMetricsVersion
)

val httpDependencies = Seq(
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http"   % AkkaHttpVersion
)

val tapir = Seq(
  "com.softwaremill.sttp.tapir" %% "tapir-akka-http-server"     % TapirVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-core"                 % TapirVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-json-circe"           % TapirVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-openapi-docs"         % TapirVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-openapi-circe-yaml"   % TapirVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui-akka-http" % TapirVersion
)

val serverDependencies = loggingDependencies ++ httpDependencies ++ monitoringDependencies ++ tapir

/// Projects

val rootProjectName     = "scalabase"
val toolKitModuleName   = "toolkit"
val serverModuleName    = "server"
val currentScalaVersion = "2.13.3"

lazy val root =
  project
    .in(file("."))
    .withId(rootProjectName)
    .aggregate(toolKit)
    .settings(
      crossScalaVersions := Nil,
      publish / skip := true
    )

lazy val toolKit =
  project
    .in(file(toolKitModuleName))
    .withId(toolKitModuleName)
    .settings(settings)
    .settings(
      libraryDependencies ++= Seq(
            compilerPlugin(Lib.kindProjector),
            compilerPlugin(Lib.betterMonadicFor),
            Lib.scalaTest
          ) ++ Lib.ConfigBundle
    )
    .enablePlugins(AutomateHeaderPlugin, BuildInfoPlugin)

lazy val server =
  project
    .in(file(serverModuleName))
    .withId(serverModuleName)
    .settings(settings)
    .settings(libraryDependencies ++= serverDependencies)
    .enablePlugins(AutomateHeaderPlugin, BuildInfoPlugin)
    .settings(buildInfoSettings)

lazy val settings =
  Seq(
    Compile / unmanagedSourceDirectories := Seq((Compile / scalaSource).value),
    semanticdbEnabled := true,
    scalafmtOnCompile := true,
    semanticdbVersion := scalafixSemanticdb.revision,
    Test / unmanagedSourceDirectories := Seq((Test / scalaSource).value),
    scalaVersion := currentScalaVersion,
    version := "0.0.1",
    organization := "me.mhoffmann",
    organizationName := "Matheus Hoffmann",
    startYear := Some(2020),
    licenses := List("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
    homepage := Some(url("https://me.hoffmann")),
    developers := List(
          Developer(
            id = "h0ffmann",
            name = "Matheus Hoffmann",
            email = "hoffmann [at] poli.ufrj.br",
            url = url("https://github.com/h0ffmann")
          )
        ),
    parallelExecution in Test := false,
    scalacOptions += "-Ywarn-unused"
  )

val stages = List("/compile", "/test", "/assembly", "/publishLocal")

addCommandAlias("publishAll", stages.map(s => toolKitModuleName + s).mkString(";+ ", ";+", ""))
addCommandAlias("slibs", "show libraryDependencies")
addCommandAlias("checkdeps", ";dependencyUpdates; reload plugins; dependencyUpdates; reload return")
addCommandAlias("fmt", "scalafmtAll")
addCommandAlias("prepare", "fix; fmt ; reload")
addCommandAlias("fix", "all compile:scalafix test:scalafix")
addCommandAlias(
  "fixCheck",
  "; compile:scalafix --check ; test:scalafix --check"
)

resolvers in ThisBuild ++= Seq(
  Resolver.defaultLocal,
  Resolver.mavenLocal,
  Resolver.mavenCentral,
  Classpaths.typesafeReleases,
  Classpaths.sbtPluginReleases
)
