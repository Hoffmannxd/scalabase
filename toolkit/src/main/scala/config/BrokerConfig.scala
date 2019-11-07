package config

import ciris.Secret
import config.CustomPredicates.Port
import eu.timepit.refined.types.string.NonEmptyString

case class BrokerConfig(hosts: List[String],
                        port: Port,
                        user: NonEmptyString,
                        password: Secret[NonEmptyString])