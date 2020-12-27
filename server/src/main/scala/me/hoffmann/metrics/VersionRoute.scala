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

// $COVERAGE-OFF$

package me.hoffmann.metrics
import akka.http.scaladsl.server.Route
import me.hoffmann.utils.Docs
import me.hoffmann.version.BuildInfo
import sttp.tapir._
import sttp.tapir.json.circe._
import sttp.tapir.server.akkahttp._
import scala.concurrent.{ ExecutionContext, Future }

/**
  * Defines an endpoint which exposes the current application version information.
  */
class VersionRoute(implicit ec: ExecutionContext) {
  import VersionRoute._
  import me.hoffmann.serde.Json._

  private val versionEndpoint: Endpoint[Unit, String, VersionDeployed, Any] =
    endpoint.get
      .in("version" / path[String]("id"))
      .out(jsonBody[VersionDeployed])
      .errorOut(stringBody)
      .description("Return current version details.")

  val r: Route = versionEndpoint.toRoute(
    _ => Future.successful(Right(VersionDeployed(BuildInfo.builtAtString, BuildInfo.lastCommitHash)))
  )

}
object VersionRoute {

  case class VersionDeployed(buildDate: String, buildSha: String)
}
