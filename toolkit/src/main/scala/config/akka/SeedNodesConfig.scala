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

package config.akka
import cats.implicits._
import ciris.refined._
import ciris.{ConfigValue, _}
import config.MultipleHost
import eu.timepit.refined.types.string.NonEmptyString

object SeedNodesConfig {

  val seedNodeConfig: ConfigValue[SeedNodesConfig] =
    env("SEED_NODES").as[NonEmptyString]
      .flatMap{
        sn => MultipleHost.hostRefined(sn.value).map(l => SeedNodesConfig(l))
      }
}
final case class SeedNodesConfig(hosts: List[String]) {

  def formatted: String = this.hosts.map(x => s""""$x"""").mkString("[", ",", "]")

}