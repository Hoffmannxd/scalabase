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

import cats.data.ValidatedNec
import cats.implicits._

object ValidationModel {

  /**
    * Validation model
    */
  abstract class ValidationFailure(val message: String)
  type FormValidation[F]   = F => ValidationResult[F]
  type ValidationResult[A] = ValidatedNec[ValidationFailure, A]
  trait Validatable[A] { def validate: ValidationResult[A] }

  /**
    * Example error custom message
    */
  case object WrongUserName extends ValidationFailure("name must be greater than x and less than y")

  /**
    * Validation of a field that can be reused in ValidationInstances.scala
    */
  def validateName(name: String): ValidationResult[String] =
    Either.cond(name.length < 5 || name.length > 30, name, WrongUserName).toValidatedNec

  /**
    * Generic method to validate a form inside another form
    * e.g. If you already defined a validation structure for BankAccount,
    * other data class containing BankAccount can be automatic validated without deduplication.
    */
  def validateForm[F, A](form: F)(f: ValidationResult[F] => A)(implicit formValidation: FormValidation[F]): A =
    f(formValidation(form))

}
