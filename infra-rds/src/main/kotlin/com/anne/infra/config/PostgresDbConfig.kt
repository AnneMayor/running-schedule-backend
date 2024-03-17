package com.anne.infra.config


import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.transaction.support.TransactionSynchronizationManager
import javax.sql.DataSource

private const val DATASOURCE_WRITER = "writer"
private const val DATASOURCE_READER = "reader"

@ConditionalOnProperty(prefix = "spring.datasource.using", name = ["runningdb"], havingValue = "true")
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = ["com.anne.domain.*.repository"],
    entityManagerFactoryRef = "runningDbEntityManager",
    transactionManagerRef = "runningDbTransactionManager"
)
class PostgresDbConfig {

    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource.runningdb.$DATASOURCE_WRITER")
    fun runningDbDataSource(): DataSource = DataSourceBuilder.create()
        .url("jdbc:postgresql://localhost:5432/postgres")
        .driverClassName("org.postgresql.Driver")
        .username("admin")
        .password("runningIsMyLife!")
        .build()

    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource.runningdb.$DATASOURCE_READER")
    fun runningDbReaderDataSource(): DataSource = DataSourceBuilder.create()
        .url("jdbc:postgresql://localhost:5432/postgres")
        .driverClassName("org.postgresql.Driver")
        .username("admin")
        .password("runningIsMyLife!")
        .build()

    @Primary
    @Bean
    fun routingDataSource(): DataSource {
        val writerDataSource: DataSource = runningDbDataSource()
        val readerDataSource: DataSource = runningDbReaderDataSource()

        val dataSourceMap = mutableMapOf<Any, Any>()
        dataSourceMap[DATASOURCE_WRITER] = writerDataSource
        dataSourceMap[DATASOURCE_READER] = readerDataSource

        val replicationRoutingDataSource = ReplicationRoutingDataSource()
        replicationRoutingDataSource.setDefaultTargetDataSource(writerDataSource)
        replicationRoutingDataSource.setTargetDataSources(dataSourceMap)
        replicationRoutingDataSource.afterPropertiesSet()

        return LazyConnectionDataSourceProxy(replicationRoutingDataSource)
    }

    @Bean
    fun runningDbTransactionManager(jpaProperties: JpaProperties, hibernateProperties: HibernateProperties) =
        JpaTransactionManager(runningDbEntityManager(jpaProperties, hibernateProperties).`object`!!)

    @Bean
    fun runningDbEntityManager(
        jpaProperties: JpaProperties,
        hibernateProperties: HibernateProperties,
    ): LocalContainerEntityManagerFactoryBean {
        val entityManagerFactoryBuilder = EntityManagerFactoryBuilder(
            HibernateJpaVendorAdapter(),
            hibernateProperties.determineHibernateProperties(jpaProperties.properties, HibernateSettings()),
            null
        )
        return entityManagerFactoryBuilder
            .dataSource(routingDataSource())
            .packages("com.anne.domain.*")
            .build()
    }
}

class ReplicationRoutingDataSource : AbstractRoutingDataSource() {

    override fun determineCurrentLookupKey(): Any {
        if (TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
            return DATASOURCE_READER
        }
        return DATASOURCE_WRITER
    }
}