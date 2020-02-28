package features.simulators

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.options

abstract class Simulator(portNumber: Int) {
    private val wireMockServer: WireMockServer = WireMockServer(options().port(portNumber))
}
