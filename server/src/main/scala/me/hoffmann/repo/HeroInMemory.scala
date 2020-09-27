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

package me.hoffmann.repo

import me.hoffmann.model.Domain

import scala.collection.mutable
import scala.concurrent.Future

class HeroInMemory extends HeroRepository[Future] {

  private var currentHero: mutable.Seq[Domain.Hero] = mutable.Seq.empty[Domain.Hero]

  override def getHero(name: String): Future[Option[Domain.Hero]] =
    Future.successful(currentHero.find(_.name == name))

  override def insertHero(hero: Domain.Hero): Future[Unit] =
    Future.successful {
      currentHero = currentHero :+ hero
    }
}
