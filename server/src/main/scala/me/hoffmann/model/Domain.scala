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

package me.hoffmann.model

import me.hoffmann.model.validation.ValidationModel.Validatable

object Domain {

  // DATA
  final case class Hero(name: String, genericClass: String, averageSpeed: Double, varSpeed: Double)

  final case class Skill(key: String, dmg: Double, cooldown: Double, range: Long)

  //IN
  final case class NewHeroReq(name: String, genericClass: String, averageSpeed: Double, varSpeed: Double)

  //OUT
  final case class HeroSkillsRes(hero: Hero, skills: List[Skill])
}
