package features

import com.ultraclearance.kotlin.Application
import io.cucumber.java8.En
import mu.KotlinLogging
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

private val LOGGER = KotlinLogging.logger {}

@ActiveProfiles("test")
@SpringBootTest(
        classes = [Application::class],
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
class SpringSetup : En {
    init {
        LOGGER.info("Application started")
    }
}
