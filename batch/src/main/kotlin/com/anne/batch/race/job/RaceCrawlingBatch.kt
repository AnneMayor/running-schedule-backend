package com.anne.batch.race.job

import com.anne.batch.race.item.RaceItem
import com.anne.batch.race.processor.RaceItemProcessor
import com.anne.batch.race.reader.RaceItemReader
import com.anne.batch.race.writer.RaceItemWriter
import com.anne.domain.race.dto.RaceDto
import com.anne.domain.race.service.RaceDomainService
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.scheduling.annotation.Scheduled
import java.time.Instant
import java.util.*

@Configuration
class RaceCrawlingBatch(
    private val transactionManager: JpaTransactionManager,
    @Value("\${spring.batch.race-crawling-chunk-size}")
    private val chunkSize: Int,
    private val raceDomainService: RaceDomainService,
    private val jobLauncher: JobLauncher,
    private val jobRepository: JobRepository,
) {
    private val raceCrawlingScheduledJob: Job by lazy { raceCrawlingBatchJob() }

    @Bean
    fun raceCrawlingBatchJob(): Job {
        return JobBuilder("race-crawling-job", jobRepository)
            .preventRestart()
            .start(raceCrawlingBatchStep())
            .incrementer(RunIdIncrementer())
            .build()
    }

    @Bean
    fun raceCrawlingBatchStep(): Step {
        return StepBuilder("race-crawling-step", jobRepository)
            .chunk<List<RaceItem>, List<RaceDto>>(chunkSize, transactionManager)
            .reader(raceItemReader())
            .processor(raceItemProcessor())
            .writer(raceItemWriter())
            .build()
    }

    // Every day at midnight(00:00 am)
    @Scheduled(cron = "1 0 0 * * *")
    fun raceCrawlingBatchJobScheduler() {
        jobLauncher.run(raceCrawlingScheduledJob, JobParametersBuilder().addDate("timestamp", Date.from(Instant.now())).toJobParameters())
            .also { println("Job finished with status: ${it.status}") }
    }

    fun raceItemReader() = RaceItemReader()

    fun raceItemProcessor() = RaceItemProcessor()

    fun raceItemWriter() = RaceItemWriter(raceDomainService = raceDomainService)
}