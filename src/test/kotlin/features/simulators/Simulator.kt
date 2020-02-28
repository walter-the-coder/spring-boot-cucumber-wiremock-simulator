package features.simulators

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.options
import com.github.tomakehurst.wiremock.http.Request
import com.github.tomakehurst.wiremock.http.ResponseDefinition
import mu.KotlinLogging

private val LOGGER = KotlinLogging.logger {}

abstract class Simulator(
        private val portNumber: Int,
        private val name: String
) {
    private lateinit var wireMockServer: WireMockServer

    val objectMapper = jacksonObjectMapper()

    lateinit var postMappings: Map<String, (request: Request) -> ResponseDefinition>

    fun start() {
        LOGGER.info("Starting $name on port $portNumber")
        wireMockServer = WireMockServer(options().port(portNumber).extensions(Transformer::class.java))
        mapStubsForPostRequests()
        wireMockServer.start()
    }

    fun stop() {
        LOGGER.info("Stopping $name running on port $portNumber")
        wireMockServer.stop()
    }

    private fun mapStubsForPostRequests() {
        if (::postMappings.isInitialized) {
            for (postMapping in postMappings) {
                wireMockServer.stubFor(
                        WireMock.post(WireMock.urlMatching(postMapping.key))
                                .willReturn(WireMock.aResponse().withTransformer(
                                        Transformer::class.java.simpleName,
                                        "requestHandler", postMapping.value
                                ))
                )
            }
        }
    }
}
