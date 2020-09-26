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

package me.hoffmann

import akka.actor.{ ActorSystem, CoordinatedShutdown }
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives.{ complete, get, path }
import com.typesafe.scalalogging.LazyLogging
import fr.davit.akka.http.metrics.core.scaladsl.server.HttpMetricsDirectives.metrics
import me.hoffmann.metrics.{ Metrics, VersionRoute }
import fr.davit.akka.http.metrics.prometheus.marshalling.PrometheusMarshallers._

import scala.concurrent.ExecutionContext
import scala.concurrent.duration.FiniteDuration
import scala.concurrent.duration._
import akka.http.scaladsl.server.Directives._
import fr.davit.akka.http.metrics.core.HttpMetrics.enrichHttp
import fr.davit.akka.http.metrics.core.scaladsl.server.HttpMetricsDirectives._

import scala.util.{ Failure, Success }

object Main extends App with LazyLogging {
  private final object BindFailure extends CoordinatedShutdown.Reason

  private val name = "pet_store"

  //TODO
  //val cluster: ActorRef[ClusterStateSubscription]

  // Config
  val host: String                        = "0.0.0.0"
  val port: Int                           = 8080
  val terminationDeadline: FiniteDuration = 10.seconds

  // Readiness

  val registry                      = Metrics.init(name)
  implicit val system: ActorSystem  = ActorSystem("system")
  val shutdown                      = CoordinatedShutdown(system)
  implicit val ec: ExecutionContext = system.dispatcher

  //var ready : Boolean = false
  val routeMetrics = (get & path("me/hoffmann/metrics"))(metrics(registry))
  val routeReady   = (get & path("ready"))(complete("Im ready"))

  val allRoutes = List(routeMetrics, routeReady, new VersionRoute().r)
    .reduce(_ ~ _)

  Http()
    .newMeteredServerAt(host, port, registry)
    .bindFlow(allRoutes)
    .onComplete {
      case Failure(cause) =>
        logger.error(s"Shutting down, because cannot bind to $host:$port!", cause)
        shutdown.run(BindFailure)

      case Success(binding) =>
        logger.info(s"Listening to HTTP connections on ${binding.localAddress}")
        binding.addToCoordinatedShutdown(terminationDeadline)
    }

}
