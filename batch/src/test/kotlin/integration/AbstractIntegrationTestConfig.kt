package integration

import com.anne.batch.BatchApplication
import com.anne.domain.common.type.EnvironmentProfiles
import io.kotest.core.listeners.AfterTestListener
import io.kotest.core.listeners.BeforeTestListener
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer

@ActiveProfiles(EnvironmentProfiles.TEST)
@SpringBootTest
@ContextConfiguration(classes = [BatchApplication::class])
class AbstractIntegrationTestConfig : BeforeTestListener, AfterTestListener {

    val postgreSQLContainer = PostgreSQLContainer("postgres:latest").apply {
        withExposedPorts(PostgreSQLContainer.POSTGRESQL_PORT)
        withUsername("admin")
        withPassword("runningIsMyLife!")
    }

    @DynamicPropertySource
    fun configureProperties(registry: DynamicPropertyRegistry) {
        registerPostgreSqlProperties(registry)
    }

    private fun registerPostgreSqlProperties(registry: DynamicPropertyRegistry) {
        registry.add("spring.datasource.runningdb.writer.driver-class-name", postgreSQLContainer::getDriverClassName)
        registry.add("spring.datasource.runningdb.writer.jdbc-url", postgreSQLContainer::getJdbcUrl)
        registry.add("spring.datasource.runningdb.writer.username", postgreSQLContainer::getUsername)
        registry.add("spring.datasource.runningdb.writer.password", postgreSQLContainer::getPassword)

        registry.add("spring.datasource.runningdb.reader.driver-class-name", postgreSQLContainer::getDriverClassName)
        registry.add("spring.datasource.runningdb.reader.jdbc-url", postgreSQLContainer::getJdbcUrl)
        registry.add("spring.datasource.runningdb.reader.username", postgreSQLContainer::getUsername)
        registry.add("spring.datasource.runningdb.reader.password", postgreSQLContainer::getPassword)
    }

    override suspend fun beforeTest(testCase: TestCase) {
        postgreSQLContainer.start()
    }

    override suspend fun afterTest(testCase: TestCase, result: TestResult) {
        postgreSQLContainer.stop()
    }
}