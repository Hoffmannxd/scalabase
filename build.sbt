import Dependencies._

ThisBuild / scalaVersion := "2.13.1"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "me.mhoffmann"
ThisBuild / organizationName := "Flönk"
ThisBuild / startYear := Some(2019)
ThisBuild / licenses := List("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0"))
ThisBuild / homepage := Some(url("https://me.hoffmann"))

lazy val settings = " gg "

lazy val root = (project in file("."))
  .settings(
    name := "utils",
    scalacOptions += "-Ymacro-annotations",
    libraryDependencies ++= Seq(
      compilerPlugin(Libraries.kindProjector),
      compilerPlugin(Libraries.betterMonadicFor)
    )
  )

lazy val commandAliases =
  addCommandAlias(
    "r1",
    """|reStart
       |---
       |-Dakka.cluster.seed-nodes.0=akka://g@127.0.0.1:25520
       |-Dakka.remote.artery.canonical.hostname=127.0.0.1
       |-Dgg.api.hostname=127.0.0.1
       |-Dgg.api.port=8080""".stripMargin
  ) ++
    addCommandAlias(
      "r2",
      """|reStart
         |---
         |-Dakka.cluster.seed-nodes.0=akka://g@127.0.0.1:25520
         |-Dakka.remote.artery.canonical.hostname=127.0.0.2
         |-Dgg.api.hostname=127.0.0.2
         |-Dgg.api.port=8080""".stripMargin
    )
