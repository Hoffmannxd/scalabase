package config

case object CassandraConfig extends BrokerClient {
  val baseName: String = "CASSANDRA"
}
