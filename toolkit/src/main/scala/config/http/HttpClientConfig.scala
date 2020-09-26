/*
 * Copyright 2020 Matheus Hoffmann
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

package config.http

import cats.implicits._
import ciris.ConfigValue
import ciris.env
import ciris.refined._
import config.CustomPredicates.Port
import eu.timepit.refined.types.string.NonEmptyString

case class HttpClientConfig(host: NonEmptyString, port: Port)

object HttpClientConfig {
  val httpClientConfig: ConfigValue[HttpClientConfig] =
    (
      env("API_HOST").as[NonEmptyString],
      env("API_PORT").as[Port]
    ).parMapN((x, y) => HttpClientConfig(x, y))
}
