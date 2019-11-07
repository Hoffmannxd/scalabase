package config

import enumeratum.{CirisEnum, Enum, EnumEntry}

sealed trait Brokers extends EnumEntry

//put name here?
object Brokers extends Enum[Brokers] with CirisEnum[Brokers] {

  case object Cassandra extends Brokers
  case object Kafka extends Brokers
  case object ElasticSearch extends Brokers

  val values: IndexedSeq[Brokers] = findValues
}