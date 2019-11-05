import sbt._

object Dependencies {

  object Versions {

    val ciris        = "1.0.1"
    val console4cats = "0.8.0"

    val betterMonadicFor = "0.3.0"
    val kindProjector    = "0.10.3"

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

    lazy val console4cats = "dev.profunktor" %% "console4cats" % Versions.console4cats

    // Compiler plugins
    lazy val betterMonadicFor = "com.olegpy"    %% "better-monadic-for" % Versions.betterMonadicFor
    lazy val kindProjector    = "org.typelevel" %% "kind-projector"     % Versions.kindProjector

    // Runtime
    lazy val logback = "ch.qos.logback" % "logback-classic" % Versions.logback

    lazy val CIRIS : Seq[ModuleID]= Seq(
      cirisBase,
      cirisEnumeratum,
      cirisRefined
    )
  }

}
