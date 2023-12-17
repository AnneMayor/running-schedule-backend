import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import java.util.*

@Configuration
class JpaAuditConfig {

    @Bean
    fun auditorProvider(): AuditorAware<String> = AuditorAware { Optional.of("0") }
}
