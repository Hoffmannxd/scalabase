/*
 * Copyright 2020 Hoffmann
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

package config.akka
import cats.implicits._
import ciris.ConfigValue
import ciris._
import ciris.refined._
import config.AppEnvironment
import config.AppEnvironment.Local
import config.MultipleHost
import eu.timepit.refined.types.string.NonEmptyString

final case class SeedNodesConfig private (hosts: List[String])

object SeedNodesConfig {

  def seedNodesConfig(environment: AppEnvironment = AppEnvironment.Local): ConfigValue[SeedNodesConfig] =
    environment match {
      case Local =>
        env("SEED_NODES")
          .as[NonEmptyString]
          .flatMap { sn =>
            MultipleHost.hostRefined(sn.value).map(l => SeedNodesConfig(l))
          }

      case _ => ConfigValue.default(SeedNodesConfig(List.empty))
    }

}
