package br.com.zumbolovsky.fateapp

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegratedTests {

    @Autowired
    protected lateinit var testRestTemplate: TestRestTemplate

    @LocalServerPort
    protected var localPort: Int = 0

    protected fun createEndpointUrl(endpoint: String) = "http://localhost:${localPort}/fate${endpoint}"

    companion object {

        @Container
        val postgreSQLContainer = PostgreSQLContainer("postgres:latest")

        @Container
        val mongoDBContainer = MongoDBContainer("mongo:latest")

        @JvmStatic
        @DynamicPropertySource
        fun overrideProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl)
            registry.add("spring.datasource.username", postgreSQLContainer::getUsername)
            registry.add("spring.datasource.password", postgreSQLContainer::getPassword)
            registry.add("mongodb.connection", mongoDBContainer::getReplicaSetUrl)
            registry.add("mongodb.database") {"test"}
        }
    }
}
