/*
 * Copyright 2019 Hoffmann
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package config.broker

import cats.implicits._
import ciris.{ env, ConfigValue }
import ciris.refined._
import config.CustomPredicates.Port
import config.{ broker, MultipleHost }
import enumeratum.{ CirisEnum, Enum, EnumEntry }
import eu.timepit.refined.auto._
import eu.timepit.refined.cats._
import eu.timepit.refined.types.string.NonEmptyString

sealed trait Brokers extends EnumEntry with Product with Serializable {
  protected val baseName: String

  lazy val brokerConfig: ConfigValue[BrokerConfig] =
    (
      env(s"${baseName}_HOST").as[NonEmptyString].flatMap(v => MultipleHost.hostRefined(v)),
      env(s"${baseName}_PORT").as[Port],
      env(s"${baseName}_USERNAME").as[NonEmptyString],
      env(s"${baseName}_PASSWORD").as[NonEmptyString].secret
    ).parMapN((x, y, z, w) => broker.BrokerConfig(x, y, z, w))
}

//put name here?
object Brokers extends Enum[Brokers] with CirisEnum[Brokers] {

  case object CassandraClient extends Brokers { val baseName: String     = "CASSANDRA" }
  case object KafkaClient extends Brokers { val baseName: String         = "KAFKA" }
  case object KafkaMetricClient extends Brokers { val baseName: String   = "KAFKA_METRIC" }
  case object ElasticSearchClient extends Brokers { val baseName: String = "ELASTICSEARCH" }

  val values: IndexedSeq[Brokers] = findValues
}
