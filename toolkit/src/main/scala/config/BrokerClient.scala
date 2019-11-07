package config

import cats.implicits._
import ciris.{ConfigValue, _}
import ciris.refined._
import config.CustomPredicates.Port
import eu.timepit.refined.auto._
import eu.timepit.refined.cats._
import eu.timepit.refined.types.string.NonEmptyString


trait BrokerClient {

  protected val baseName: String

  private val brokerConfig: ConfigValue[BrokerConfig] =
    (
        env(s"${baseName}_HOST").as[NonEmptyString].flatMap(v => MultipleHost.hostRefined(v)),
        env(s"${baseName}_PORT").as[Port],
        env(s"${baseName}_USERNAME").as[NonEmptyString],
        env(s"${baseName}_PASSWORD").as[NonEmptyString].secret
      ).parMapN((x,y,z,w) => BrokerConfig(x,y,z,w))
}