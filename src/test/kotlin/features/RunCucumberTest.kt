package features

import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.runner.RunWith
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@RunWith(Cucumber::class)
@CucumberOptions(
        strict = true,
        glue = ["features"],
        stepNotifications = true,
        features = ["src/test/resources/features"],
        plugin = ["pretty"]
)
class RunCucumberTest
