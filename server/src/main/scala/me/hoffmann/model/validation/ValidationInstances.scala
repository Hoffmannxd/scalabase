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

package me.hoffmann.model.validation

import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.unmarshalling.{ FromRequestUnmarshaller, Unmarshaller }
import cats.data.Validated.{ Invalid, Valid }
import cats.implicits._
import me.hoffmann.model.Domain.NewHeroReq
import me.hoffmann.model.validation.ValidationModel.{ validateForm, validateName, FormValidation }
import scala.concurrent.Future

object ValidationInstances {

  /**
    * Machinery necessary to translate a list of failures into a fail HttpResponse.
    */
  implicit class ValidationRequestMarshaller[A](um: FromRequestUnmarshaller[A]) {
    def validateEntity(implicit validation: FormValidation[A]): Unmarshaller[HttpRequest, A] = um.flatMap {
      _ => _ => entity =>
        validateForm(entity) {
          case Valid(_) => Future.successful(entity)
          case Invalid(failures) =>
            Future.failed(new IllegalArgumentException(failures.iterator.map(_.message).mkString("\n")))
        }
    }
  }

  implicit val valNewHero: FormValidation[NewHeroReq] = {
    case NewHeroReq(n, c, as, vs) =>
      (
        validateName(n),
        c.valid,
        as.valid,
        vs.valid
      ).mapN(NewHeroReq)
  }

}
