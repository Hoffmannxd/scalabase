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

package utils
import cats.effect.{ExitCode, IO, IOApp}
import config.akka.SeedNodesConfig._
import cats.implicits._

object Main extends IOApp {
 def sayMyName: String = {
   //val k = 21
   " aa"
 }

  println(sayMyName)

  def run(args: List[String]): IO[ExitCode] = {
    //SeedNodesConfig(List.empty)
    seedNodesConfig().load[IO].map(x => println(x)).as(ExitCode.Success)
  }
}
