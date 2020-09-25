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

import _root_.metrics.Metrics
import akka.actor.{ ActorSystem, CoordinatedShutdown }
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCode
import akka.http.scaladsl.server.Directives.{ complete, get, path, _ }
import com.typesafe.scalalogging.LazyLogging
import fr.davit.akka.http.metrics.core.HttpMetrics._
import fr.davit.akka.http.metrics.core.scaladsl.server.HttpMetricsDirectives.metrics
import fr.davit.akka.http.metrics.prometheus.marshalling.PrometheusMarshallers._

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._
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
  val routeMetrics = (get & path("metrics"))(metrics(registry))
  val routeReady   = (get & path("ready"))(complete("Im ready"))

  val allRoutes = List(routeMetrics, routeReady)
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
