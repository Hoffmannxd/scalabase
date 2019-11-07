package config

import config.AppEnvironment.findValues
import enumeratum.{CirisEnum, Enum, EnumEntry}

object Http {
  sealed trait Version extends EnumEntry

  object Version extends Enum[Version] with CirisEnum[Version] {
    case object Http10 extends Version
    case object Http11 extends Version
    case object Http20 extends Version

    val values: IndexedSeq[Version] = findValues
  }

  sealed trait Scheme extends EnumEntry

  object Scheme extends Enum[Scheme] with CirisEnum[Scheme] {
    case object Http extends Scheme
    case object Https extends Scheme

    val values: IndexedSeq[Scheme] = findValues
  }
}
