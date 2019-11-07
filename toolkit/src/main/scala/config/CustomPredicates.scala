package config

import ciris.Secret
import eu.timepit.refined.api.Refined
import eu.timepit.refined.boolean.{And, Or}
import eu.timepit.refined.collection.{Forall, NonEmpty}
import eu.timepit.refined.numeric.Positive
import eu.timepit.refined.string.{IPv4, Url}
import eu.timepit.refined.types.string.NonEmptyString

object CustomPredicates {

  type Password = Secret[NonEmptyString]
  type HostList = NonEmpty And Forall[Or[IPv4, Url]]
  type Port = Int Refined Positive

}
