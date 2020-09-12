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

package config

import ciris.ConfigError
import ciris.ConfigValue
import config.CustomPredicates.HostList
import eu.timepit.refined.refineV

object MultipleHost {

  val hostRefined: String => ConfigValue[List[String]] = string => {
    refineV[HostList](string.split(",").toList.map(_.replace(" ", "")))
      .fold(
        error => ConfigValue.failed[List[String]](ConfigError(error)),
        correct => ConfigValue.default[List[String]](correct.value)
      )
  }

  def formatted: List[String] => String = hosts => hosts.map(x => s""""$x"""").mkString("[", ",", "]")

}
