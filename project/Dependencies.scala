import sbt._

object Dependencies {

  object Versions {

    val ciris        = "1.0.1"
    val console4cats = "0.8.0"
    val refined          = "0.9.10"

    val betterMonadicFor = "0.3.0"
    val kindProjector    = "0.10.3"

    val scalaTest           = "3.2.0-M1"
    val logback = "1.2.1"
  }

  object Lib {

    lazy val cirisBase           = "is.cir" %% "ciris"             % Versions.ciris
    lazy val cirisCats           = "is.cir" %% "ciris-cats"        % Versions.ciris
    lazy val cirisCatsEffect     = "is.cir" %% "ciris-cats-effect" % Versions.ciris
    lazy val cirisCore           = "is.cir" %% "ciris-core"        % Versions.ciris
    lazy val cirisEnumeratum     = "is.cir" %% "ciris-enumeratum"  % Versions.ciris
    lazy val cirisGeneric        = "is.cir" %% "ciris-generic"     % Versions.ciris
    lazy val cirisRefined        = "is.cir" %% "ciris-refined"     % Versions.ciris

    lazy val refinedCore = "eu.timepit" %% "refined"      % Versions.refined
    lazy val refinedCats = "eu.timepit" %% "refined-cats" % Versions.refined

    lazy val console4cats = "dev.profunktor" %% "console4cats" % Versions.console4cats

    // Compiler plugins
    lazy val betterMonadicFor = "com.olegpy"    %% "better-monadic-for" % Versions.betterMonadicFor
    lazy val kindProjector    = "org.typelevel" %% "kind-projector"     % Versions.kindProjector

    lazy val scalaTest = "org.scalatest" %% "scalatest" % Versions.scalaTest % Test
    lazy val logback = "ch.qos.logback" % "logback-classic" % Versions.logback

    lazy val CONFIG : Seq[ModuleID]= Seq(
      cirisBase,
      refinedCore,
      refinedCats,
      cirisEnumeratum,
      cirisRefined
    )
  }

}
