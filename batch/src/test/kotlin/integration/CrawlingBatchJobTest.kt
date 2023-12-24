package integration

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.JobParameters
import org.springframework.batch.test.JobLauncherTestUtils
import org.springframework.batch.test.context.SpringBatchTest


@SpringBatchTest
class CrawlingBatchJobTest : FunSpec({
    extensions(AbstractIntegrationTestConfig())

    test("마라톤 대회 크롤링 배치 테스트 - Reader") {
        val jobLauncherTestUtils = JobLauncherTestUtils()

        val jobParameters = JobParameters()

        // 배치 작업 실행
        val jobExecution = jobLauncherTestUtils.launchJob(jobParameters)

        // 테스트 결과 검증
        jobExecution.status shouldBe BatchStatus.COMPLETED

    }

})