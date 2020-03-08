package io.zeebe.zeeqs

import io.zeebe.zeeqs.importer.hazelcast.HazelcastImporter
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import javax.annotation.PostConstruct

@SpringBootApplication
@EnableCaching
@EnableConfigurationProperties(HazelcastProperties::class)
class ZeeqlApplication(
        val hazelcastProperties: HazelcastProperties,
        val hazelcastImporter: HazelcastImporter
) {
    val logger = LoggerFactory.getLogger(ZeeqlApplication::class.java)

    @PostConstruct
    fun init() {
        val connection = hazelcastProperties.connection

        logger.info("connect to Hazelcast: '$connection'")
        hazelcastImporter.start(connection)
    }
}

fun main(args: Array<String>) {
    runApplication<ZeeqlApplication>(*args)
}
