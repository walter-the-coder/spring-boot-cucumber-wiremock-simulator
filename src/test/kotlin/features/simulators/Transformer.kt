package features.simulators

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder
import com.github.tomakehurst.wiremock.common.FileSource
import com.github.tomakehurst.wiremock.extension.Parameters
import com.github.tomakehurst.wiremock.extension.ResponseDefinitionTransformer
import com.github.tomakehurst.wiremock.http.Request
import com.github.tomakehurst.wiremock.http.ResponseDefinition
import mu.KotlinLogging

private val LOGGER = KotlinLogging.logger {}

class Transformer : ResponseDefinitionTransformer() {
    override fun getName() = "Transformer"

    override fun transform(
            request: Request?,
            responseDefinition: ResponseDefinition?,
            fileSource: FileSource?,
            parameters: Parameters?
    ): ResponseDefinition {
        require(request != null)
        LOGGER.info("Received request at url=${request.url} with method=${request.method}")

        if (responseDefinition == null || parameters == null) {
            return ResponseDefinitionBuilder().build()
        }

        val requestHandler = parameters["requestHandler"] as (request: Request) -> ResponseDefinition
        return requestHandler(request)
    }
}
