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

package me.hoffmann.utils

import me.hoffmann.version.BuildInfo
import sttp.tapir.Endpoint
import sttp.tapir.docs.openapi._
import sttp.tapir.openapi.circe.yaml.RichOpenAPI

object Docs {
  def show(ep: Endpoint[_, _, _, _]): Unit = {
    val openapi = List(ep).toOpenAPI("Sample docs", BuildInfo.version)
    val yaml    = openapi.toYaml
    println(yaml)
  }

}
