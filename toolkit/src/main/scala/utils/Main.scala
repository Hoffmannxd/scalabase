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

package utils
import cats.effect.ExitCode
import cats.effect.IO
import cats.effect.IOApp
//import config.akka.SeedNodesConfig._
//import cats.implicits._
//import config.broker.Brokers.{ CassandraClient, KafkaClient }

object Main extends IOApp {
  val sayMyName                             = " aa"
  def run(args: List[String]): IO[ExitCode] =
    //SeedNodesConfig(List.empty)
    //CassandraClient.brokerConfig.load[IO].map(x => println(x)).as(ExitCode.Success)
    for {
      _ <- IO.unit
      //cass <- CassandraClient.brokerConfig.load[IO]
      //kafka <- KafkaClient.brokerConfig.load[IO]
      _ = println(sayMyName)
    } yield {
      ExitCode.Success
    }
}
