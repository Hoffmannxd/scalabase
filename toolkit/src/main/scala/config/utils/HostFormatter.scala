package config.utils

object HostFormatter {

  def removeProtocol(host: String): String =
    if (host.replace(".", "").forall(k => k.isDigit)) {
      host
    } else {
      host.split("//").last
    }

  def removeProtocolFromList(list: String*): List[String] =
    list.toList.map { item =>
      removeProtocol(item)
    }
}
