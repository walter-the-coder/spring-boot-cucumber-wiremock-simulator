package features

import com.example.kotlin.Application
import features.simulators.InformationProcessorSimulator
import io.cucumber.java8.En
import mu.KotlinLogging
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

private val LOGGER = KotlinLogging.logger {}

@ActiveProfiles("test")
@SpringBootTest(
        classes = [Application::class],
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class SpringSetup : En {
    init {
        Before { _ ->
            InformationProcessorSimulator.start()
        }

        After { _ ->
            InformationProcessorSimulator.stop()
        }

        LOGGER.info("Application started")
    }
}
