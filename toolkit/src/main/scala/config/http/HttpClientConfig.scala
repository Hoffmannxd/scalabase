package config.http

import ciris.{ env, ConfigValue }
import config.CustomPredicates.Port
import eu.timepit.refined.types.string.NonEmptyString
import cats.implicits._
import ciris.{ env, ConfigValue }
import ciris.refined._
import config.CustomPredicates.Port
import config.{ broker, MultipleHost }
import enumeratum.{ CirisEnum, Enum, EnumEntry }
import eu.timepit.refined.auto._
import eu.timepit.refined.cats._
import eu.timepit.refined.types.string.NonEmptyString

case class HttpClientConfig(host: NonEmptyString, port: Port)

object HttpClientConfig {
  val httpClientConfig: ConfigValue[HttpClientConfig] =
    (
      env("API_HOST").as[NonEmptyString],
      env("API_PORT").as[Port]
    ).parMapN((x, y) => HttpClientConfig(x, y))
}
