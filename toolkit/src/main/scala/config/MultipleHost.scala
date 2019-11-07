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

package config

import ciris.{ConfigError, ConfigValue}
import eu.timepit.refined.boolean.{And, Or}
import eu.timepit.refined.collection.{Forall, NonEmpty}
import eu.timepit.refined.refineV
import eu.timepit.refined.string.{IPv4, Url}

object MultipleHost {
  type HostList = NonEmpty And Forall[Or[IPv4, Url]]

  val hostRefined: String => ConfigValue[List[String]] = string => {
    refineV[HostList](string.split(",").toList.map(_.replace(" ", "")))
      .fold(
        error => ConfigValue.failed[List[String]](ConfigError(error))
        ,
        correct => ConfigValue.default[List[String]](correct.value)
      )
  }
}