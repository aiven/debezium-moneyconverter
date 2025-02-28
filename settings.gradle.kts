rootProject.name = "debezium-moneyconverter"

val assertJVersion by extra("3.26.3")
val debeziumVersion by extra("2.5.0.Final")
val junitVersion by extra("5.11.4")
val kafkaVersion by extra("1.1.0")

dependencyResolutionManagement {
  versionCatalogs {
    create("apache") { library("kafka-connect-api", "org.apache.kafka:connect-api:$kafkaVersion") }
    create("debezium") { library("debezium-api", "io.debezium:debezium-api:$debeziumVersion") }
    create("testinglibs") {
      library("junit-jupiter", "org.junit.jupiter:junit-jupiter:$junitVersion")
      library("junit-jupiter-engine", "org.junit.jupiter:junit-jupiter-engine:$junitVersion")
      library("assertj", "org.assertj:assertj-core:$assertJVersion")
    }
  }
}
