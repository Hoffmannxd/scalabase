package config

import cats.implicits._
import ciris.{ConfigValue, _}
import ciris.refined._
import config.CustomPredicates.Port
import eu.timepit.refined.auto._
import eu.timepit.refined.cats._
import eu.timepit.refined.types.string.NonEmptyString

object BrokerConfig {
  case class BrokerConfig(hosts: List[String],
                          port: Port,
                          user: NonEmptyString,
                          password: Secret[NonEmptyString])// extends Broker

}

abstract class BrokerConfig(baseName: String) {


  val brokerConfig: ConfigValue[BrokerConfig.BrokerConfig] =
    (
        env(s"${baseName}_HOST").as[NonEmptyString].flatMap(v => MultipleHost.hostRefined(v)),
        env(s"${baseName}_PORT").as[Port],
        env(s"${baseName}_USERNAME").as[NonEmptyString],
        env(s"${baseName}_PASSWORD").as[NonEmptyString].secret
      ).parMapN((x,y,z,w) => BrokerConfig.BrokerConfig(x,y,z,w))
}